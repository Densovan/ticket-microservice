package com.denkh.user.service.impl;

import com.denkh.user.constant.Constant;
import com.denkh.user.dto.request.CreatePermissionRequestDto;
import com.denkh.user.dto.response.CreatePermissionResponseDto;
import com.denkh.user.entity.Permission;
import com.denkh.user.entity.Role;
import com.denkh.user.repository.GroupRepository;
import com.denkh.user.repository.PermissionRepository;
import com.denkh.user.repository.RoleRepository;
import com.denkh.user.service.PermissionService;
import jakarta.transaction.SystemException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final PermissionRepository permissionRepository;
    private final RoleRepository roleRepository;

    @Override
    public CreatePermissionResponseDto create(CreatePermissionRequestDto permissionRequestDto) {
        if (permissionRepository.existsByName(permissionRequestDto.getName())) {
            throw new IllegalArgumentException("Permission with name " + permissionRequestDto.getName() + " already exists");
        }
        Permission permission = mapToPermission(permissionRequestDto);
        permissionRepository.save(permission);
        return mapToPermissionResponse(permission);
    }

    @Override
    public CreatePermissionResponseDto update(Long id, CreatePermissionRequestDto permissionRequestDto) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found with id: " + id));

        // Check if the new name conflicts with existing permissions (excluding current permission)
        if (!permission.getName().equals(permissionRequestDto.getName()) &&
                permissionRepository.existsByName(permissionRequestDto.getName())) {
            throw new IllegalArgumentException("Permission with name '" + permissionRequestDto.getName() + "' already exists");
        }

        permission.setName(permissionRequestDto.getName());
        permission.setDescription(permissionRequestDto.getDescription());
        permission.setStatus(permissionRequestDto.getStatus());

        permissionRepository.save(permission);

        return mapToPermissionResponse(permission);
    }

    @Override
    public CreatePermissionResponseDto assignRoleToPermission(Long permissionId, Long roleId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found with id: " + permissionId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));

        permission.addRole(role);
        permissionRepository.save(permission);

        return mapToPermissionResponse(permission);
    }

    @Override
    public CreatePermissionResponseDto removeRoleFromPermission(Long permissionId, Long roleId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new IllegalArgumentException("Permission not found with id: " + permissionId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new IllegalArgumentException("Role not found with id: " + roleId));

        permission.removeRole(role);
        permissionRepository.save(permission);

        return mapToPermissionResponse(permission);
    }

    @Override
    public List<Permission> getPermissionsByNameIn(Set<String> names) {
        return permissionRepository.findAllByStatusAndNameIn(Constant.ACTIVE, names);
    }

    private Permission mapToPermission(CreatePermissionRequestDto request) {
        Permission permission = new Permission();
        permission.setName(request.getName());
        permission.setDescription(request.getDescription());
        permission.setStatus(Constant.ACTIVE);
        return permission;
    }

    private CreatePermissionResponseDto mapToPermissionResponse(Permission permission) {
        CreatePermissionResponseDto responseDto = new CreatePermissionResponseDto();
        responseDto.setId(permission.getId());
        responseDto.setName(permission.getName());
        responseDto.setDescription(permission.getDescription());
        responseDto.setStatus(permission.getStatus());
        return responseDto;
    }
}

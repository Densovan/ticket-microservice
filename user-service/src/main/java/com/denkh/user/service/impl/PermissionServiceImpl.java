package com.denkh.user.service.impl;

import com.denkh.common.constant.ApiConstant;
import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.common.exception.SystemException;
import com.denkh.user.constant.Constant;
import com.denkh.user.dto.request.CreatePermissionRequest;
import com.denkh.user.dto.response.PermissionResponse;
import com.denkh.user.entity.Permission;
import com.denkh.user.entity.Role;
import com.denkh.user.repository.PermissionRepository;
import com.denkh.user.repository.RoleRepository;
import com.denkh.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
@RequiredArgsConstructor
public class PermissionServiceImpl implements PermissionService {

    private final RoleRepository roleRepository;
    private final PermissionRepository permissionRepository;

    @Override
    public ResponseErrorTemplate create(CreatePermissionRequest permissionRequest) {
        if (permissionRepository.existsByName(permissionRequest.name())) {
            throw new SystemException("Permission with name '" + permissionRequest.name() + "' already exists");
        }
        Permission permission = mapToPermission(permissionRequest);
        permissionRepository.save(permission);

        return constructPermissionResponse(permission);
    }

    @Override
    public ResponseErrorTemplate update(Long id, CreatePermissionRequest permissionDetails) {
        Permission permission = permissionRepository.findById(id)
                .orElseThrow(() -> new SystemException("Permission not found with id: " + id));

        // Check if the new name conflicts with existing permissions (excluding current permission)
        if (!permission.getName().equals(permissionDetails.name()) &&
                permissionRepository.existsByName(permissionDetails.name())) {
            throw new SystemException("Permission with name '" + permissionDetails.name() + "' already exists");
        }

        permission.setName(permissionDetails.name());
        permission.setDescription(permissionDetails.description());
        permission.setStatus(permissionDetails.status());

        permissionRepository.save(permission);

        return constructPermissionResponse(permission);
    }

    @Override
    public ResponseErrorTemplate assignRoleToPermission(Long permissionId, Long roleId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new SystemException("Permission not found with id: " + permissionId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new SystemException("Role not found with id: " + roleId));

        permission.addRole(role);
        permissionRepository.save(permission);

        return constructPermissionResponse(permission);
    }

    @Override
    public ResponseErrorTemplate removeRoleFromPermission(Long permissionId, Long roleId) {
        Permission permission = permissionRepository.findById(permissionId)
                .orElseThrow(() -> new SystemException("Permission not found with id: " + permissionId));

        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new SystemException("Role not found with id: " + roleId));

        permission.removeRole(role);
        permissionRepository.save(permission);

        return constructPermissionResponse(permission);
    }

    @Override
    public List<Permission> getPermissionsByNameIn(Set<String> names) {
        return permissionRepository.findAllByStatusAndNameIn(Constant.ACTIVE, names);
    }

    private Permission mapToPermission(CreatePermissionRequest request) {
        Permission permission = new Permission();
        permission.setName(request.name());
        permission.setDescription(request.description());
        permission.setStatus(Constant.ACTIVE);
        return permission;
    }

    private ResponseErrorTemplate constructPermissionResponse(Permission permission) {
        PermissionResponse permissionResponse = new PermissionResponse(
                permission.getId(),
                permission.getName(),
                permission.getDescription(),
                permission.getStatus(),
                permission.getRoles(),
                permission.getGroups()
        );

        return new ResponseErrorTemplate(
                ApiConstant.SUCCESS.getDescription(),
                ApiConstant.SUCCESS.getKey(),
                permissionResponse,
                false
        );
    }

}
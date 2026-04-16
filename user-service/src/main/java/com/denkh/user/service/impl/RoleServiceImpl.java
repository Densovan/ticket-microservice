package com.denkh.user.service.impl;

import com.denkh.user.constant.Constant;
import com.denkh.user.dto.request.CreateRoleRequestDto;
import com.denkh.user.dto.response.CreateRoleResponseDto;
import com.denkh.user.entity.Role;
import com.denkh.user.exception.RoleValidationException;
import com.denkh.user.mapper.RoleMapper;
import com.denkh.user.repository.RoleRepository;
import com.denkh.user.service.RoleService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.List;
import java.util.Set;

@Slf4j
@Service
public class RoleServiceImpl implements RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public RoleServiceImpl(RoleRepository roleRepository, RoleMapper roleMapper) {
        this.roleRepository = roleRepository;
        this.roleMapper = roleMapper;
    }

    @Override
    public CreateRoleResponseDto create(CreateRoleRequestDto createRoleRequestDto) {
        if (!StringUtils.hasText(createRoleRequestDto.getName())) {
            throw new RoleValidationException("name", "Role name cannot be empty");
        }
        if (roleRepository.findByName(createRoleRequestDto.getName()).isPresent()) {
            throw new RoleValidationException("name", "Role already exists");
        }

        Role role = roleMapper.toRole(createRoleRequestDto);
        role.setStatus(Constant.ACTIVE);
        roleRepository.save(role);
        return roleMapper.toCreateRoleResponseDto(role);
    }

    @Override
    public CreateRoleResponseDto update(Long id, CreateRoleRequestDto createRoleRequestDto) {
        Role role = roleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Role not found with id: " + id));

        final String roleName = createRoleRequestDto.getName();

        if (!role.getName().equals(roleName) &&
                roleRepository.existsByName(createRoleRequestDto.getName())) {
            throw new IllegalArgumentException("Role with name " + roleName + " already exists");
        }

        role.setName(roleName);
        role.setStatus(Constant.ACTIVE);
        role.setDescription(createRoleRequestDto.getDescription());
        roleRepository.save(role);
        return roleMapper.toCreateRoleResponseDto(role);
    }

    @Override
    public CreateRoleResponseDto findById(Long id) {
        Role role = roleRepository.findById(id).orElseThrow(() ->
                new IllegalArgumentException("Role not found with id: " + id));
        return roleMapper.toCreateRoleResponseDto(role);
    }

    @Override
    public CreateRoleResponseDto findByName(String name) {
        return roleRepository.findByName(name)
                .map(roleMapper::toCreateRoleResponseDto)
                .orElse(null);
    }

    @Override
    public List<Role> findByNameIn(Set<String> roleNames) {
        return roleRepository.findAllByNameIn(roleNames);
    }

    @Override
    public List<CreateRoleResponseDto> findAll() {
        List<Role> roles = roleRepository.findAll();
        return convertToResponseList(roles);
    }

    @Override
    public List<CreateRoleResponseDto> findAllRoleActive(String status) {
        List<Role> roles = roleRepository.findAllByStatus(status);
        return convertToResponseList(roles);
    }

    private List<CreateRoleResponseDto> convertToResponseList(List<Role> roles) {

        if (roles.isEmpty()) {
            return List.of();
        }
        return roles.stream()
                .map(roleMapper::toCreateRoleResponseDto).toList();
    }
}

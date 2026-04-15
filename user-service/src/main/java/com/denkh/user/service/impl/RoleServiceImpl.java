package com.denkh.user.service.impl;

import com.denkh.user.constant.Constant;
import com.denkh.user.dto.request.CreateRoleRequestDto;
import com.denkh.user.dto.response.CreateRoleResponseDto;
import com.denkh.user.entity.Role;
import com.denkh.user.exception.RoleValidationException;
import com.denkh.user.mapper.RoleMapper;
import com.denkh.user.repository.RoleRepository;
import com.denkh.user.service.RoleService;
import com.denkh.user.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;


import java.util.List;

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
        if(roleRepository.findByName(createRoleRequestDto.getName()).isPresent()) {
            throw new RoleValidationException("name", "Role already exists");
        }

        Role role = roleMapper.toRole(createRoleRequestDto);
        role.setStatus(Constant.ACTIVE);
        roleRepository.save(role);
        return roleMapper.toCreateRoleResponseDto(role);
    }

    @Override
    public CreateRoleResponseDto update(Long id, CreateRoleRequestDto createRoleRequestDto) {

        return null;
    }

    @Override
    public CreateRoleResponseDto findById(Long id) {
        return null;
    }

    @Override
    public CreateRoleResponseDto findByName(String name) {
        return roleRepository.findByName(name)
                .map(roleMapper::toCreateRoleResponseDto)
                .orElse(null);
    }

    @Override
    public List<CreateRoleResponseDto> findAll() {
        return List.of();
    }

    @Override
    public List<CreateRoleResponseDto> findAllRoleActive(String status) {
        return List.of();
    }
}

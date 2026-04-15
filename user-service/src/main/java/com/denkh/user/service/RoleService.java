package com.denkh.user.service;

import com.denkh.user.dto.request.CreateRoleRequestDto;
import com.denkh.user.dto.response.CreateRoleResponseDto;

import java.util.List;

public interface RoleService {
    CreateRoleResponseDto create(CreateRoleRequestDto createRoleRequestDto);

    CreateRoleResponseDto update(Long id, CreateRoleRequestDto createRoleRequestDto);

    CreateRoleResponseDto findById(Long id);

    CreateRoleResponseDto findByName(String name);

    List<CreateRoleResponseDto> findAll();

    List<CreateRoleResponseDto> findAllRoleActive(String status);
}

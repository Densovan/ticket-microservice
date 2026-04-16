package com.denkh.user.service;

import com.denkh.user.dto.request.CreatePermissionRequestDto;
import com.denkh.user.dto.response.CreatePermissionResponseDto;
import com.denkh.user.entity.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {

    CreatePermissionResponseDto create(CreatePermissionRequestDto permissionRequestDto);

    CreatePermissionResponseDto update(Long id, CreatePermissionRequestDto permissionRequestDto);

    CreatePermissionResponseDto assignRoleToPermission(Long permissionId, Long roleId);

    CreatePermissionResponseDto removeRoleFromPermission(Long permissionId, Long roleId);

    List<Permission> getPermissionsByNameIn(Set<String> names);


}

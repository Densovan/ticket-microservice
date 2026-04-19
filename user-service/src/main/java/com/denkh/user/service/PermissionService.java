package com.denkh.user.service;

import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.user.dto.request.CreatePermissionRequest;
import com.denkh.user.entity.Permission;

import java.util.List;
import java.util.Set;

public interface PermissionService {

    ResponseErrorTemplate create(CreatePermissionRequest permissionRequest);

    ResponseErrorTemplate update(Long id, CreatePermissionRequest permissionRequest);

    ResponseErrorTemplate assignRoleToPermission(Long permissionId, Long roleId);

    ResponseErrorTemplate removeRoleFromPermission(Long permissionId, Long roleId);

    List<Permission> getPermissionsByNameIn(Set<String> names);
}
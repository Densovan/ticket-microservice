package com.denkh.user.controller;

import com.denkh.common.dto.ApiResponse;
import com.denkh.user.dto.request.CreatePermissionRequestDto;
import com.denkh.user.dto.response.CreatePermissionResponseDto;
import com.denkh.user.service.PermissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/permissions")
@RequiredArgsConstructor
public class PermissionController {
    private final PermissionService permissionService;




    @PostMapping
    public ResponseEntity<ApiResponse<CreatePermissionResponseDto>> createPermission(@RequestBody CreatePermissionRequestDto permission) {
        return ResponseEntity.ok(ApiResponse.success("Created Permission Successfully",permissionService.create(permission)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CreatePermissionResponseDto>> updatePermission(@PathVariable Long id,
                                                                  @RequestBody CreatePermissionRequestDto permissionDetails) {
        return ResponseEntity.ok(ApiResponse.success("Updated Permission Successfully",permissionService.update(id, permissionDetails)));
    }


    @PostMapping("/{permissionId}/roles/{roleId}")
    public ResponseEntity<ApiResponse<CreatePermissionResponseDto>> assignRoleToPermission(@PathVariable Long permissionId,
                                                                        @PathVariable Long roleId) {
        return ResponseEntity.ok(ApiResponse.success("Assigned Role Successfully",permissionService.assignRoleToPermission(permissionId, roleId)));
    }

    @DeleteMapping("/{permissionId}/roles/{roleId}")
    public ResponseEntity<ApiResponse<CreatePermissionResponseDto>> removeRoleFromPermission(@PathVariable Long permissionId,
                                                                          @PathVariable Long roleId) {
        return ResponseEntity.ok(ApiResponse.success("Removed Role Permission Successfully", permissionService.removeRoleFromPermission(permissionId, roleId)));
    }


}

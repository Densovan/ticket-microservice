package com.denkh.user.controller;

import com.denkh.user.dto.request.CreateRoleRequestDto;
import com.denkh.user.dto.response.CreateRoleResponseDto;
import com.denkh.user.service.RoleService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/roles")
public class RoleController {
    private final RoleService roleService;

    public RoleController(RoleService roleService) {
        this.roleService = roleService;
    }

    @PostMapping("/create")
    public ResponseEntity<CreateRoleResponseDto> createRole(@Valid @RequestBody CreateRoleRequestDto createRoleRequestDto) {
        CreateRoleResponseDto createRoleResponseDto = roleService.create(createRoleRequestDto);
        return ResponseEntity.ok(createRoleResponseDto);
    }
}

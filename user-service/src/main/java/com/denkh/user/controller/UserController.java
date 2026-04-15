package com.denkh.user.controller;

import com.denkh.user.dto.request.CreateUserRequestDto;
import com.denkh.user.dto.response.AuthResponse;
import com.denkh.user.dto.response.CreateUserResponseDto;
import com.denkh.user.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/register")
    private ResponseEntity<AuthResponse> createUser(@Valid @RequestBody CreateUserRequestDto request) {
        AuthResponse userResponseDto = userService.create(request);
        return ResponseEntity.ok(userResponseDto);
    }
}

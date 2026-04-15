package com.denkh.user.service;

import com.denkh.user.dto.request.CreateUserRequestDto;
import com.denkh.user.dto.response.AuthResponse;


public interface UserService {

    AuthResponse create(CreateUserRequestDto createUserRequestDto);
    AuthResponse update(Long id, CreateUserRequestDto createUserRequestDto);

}

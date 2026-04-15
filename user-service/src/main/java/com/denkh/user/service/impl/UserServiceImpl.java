package com.denkh.user.service.impl;

import com.denkh.user.constant.Constant;
import com.denkh.user.dto.request.CreateUserRequestDto;
import com.denkh.user.dto.response.AuthResponse;
import com.denkh.user.dto.response.CreateRoleResponseDto;
import com.denkh.user.dto.response.CreateUserResponseDto;
import com.denkh.user.entity.Role;
import com.denkh.user.entity.User;
import com.denkh.user.exception.UserValidationException;
import com.denkh.user.mapper.UserMapper;
import com.denkh.user.repository.RoleRepository;
import com.denkh.user.repository.UserRepository;
import com.denkh.user.service.RoleService;
import com.denkh.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final RoleService roleService;
    private final UserMapper userMapper;


    @Override
    public AuthResponse create(CreateUserRequestDto createUserRequestDto) {
        if (!StringUtils.hasText(createUserRequestDto.getUsername())) {
            throw new UserValidationException("username", "Username must not be empty");
        }

        if (!StringUtils.hasText(createUserRequestDto.getPassword())) {
            throw new UserValidationException("password", "Password must not be empty");
        }

        if (userRepository.findByUsername(createUserRequestDto.getUsername()).isPresent()) {
            throw new UserValidationException("username", "Username already exists");
        }

        User user = userMapper.toUser(createUserRequestDto);
        user.setStatus(Constant.ACTIVE);

        //handle role
        if ( createUserRequestDto.getRoles() == null || createUserRequestDto.getRoles().isEmpty()) {
            roleRepository.findByName(Constant.USER).ifPresent(user::addRole);
        } else {
            roleRepository.findAllByNameIn(createUserRequestDto.getRoles()).forEach(user::addRole);
        }
        userRepository.save(user);

        return new AuthResponse();
    }

    @Override
    public AuthResponse update(Long id, CreateUserRequestDto createUserRequestDto) {
        return null;
    }
}

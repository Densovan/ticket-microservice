package com.denkh.user.handler;


import com.denkh.common.dto.EmptyObject;
import com.denkh.common.dto.UserResponse;
import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.user.dto.request.CreateUserRequestDto;
import com.denkh.user.dto.response.CreateUserResponseDto;
import com.denkh.user.entity.Role;
import com.denkh.user.entity.User;
import com.denkh.user.repository.RoleRepository;
import com.denkh.user.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;
import java.util.Optional;



@Slf4j
@Service
public class UserHandlerService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserHandlerService(PasswordEncoder passwordEncoder,
                              UserRepository userRepository,
                              RoleRepository roleRepository) {
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public ResponseErrorTemplate userRequestValidation(CreateUserRequestDto userRequest) {

        if(ObjectUtils.isEmpty(userRequest.getPassword())) {
            return new ResponseErrorTemplate(
                    "Password can't be blank or null.",
                    String.valueOf(HttpStatus.BAD_REQUEST),
                    new EmptyObject(),
                    true);
        }

        Optional<User> user = userRepository.findByUsernameOrEmail(userRequest.getUsername(), userRequest.getEmail());
        if(user.isPresent()){
            return new ResponseErrorTemplate(
                    "Username or Email already exists.",
                    String.valueOf(HttpStatus.BAD_REQUEST),
                    new EmptyObject(),
                    true);
        }

        List<String> roles = roleRepository.findAll().stream().map(Role::getName).toList();
        for(var role : userRequest.getRoles()){
            if(!roles.contains(role)) {
                return new ResponseErrorTemplate(
                        "Role is invalid request.",
                        String.valueOf(HttpStatus.BAD_REQUEST),
                        new EmptyObject(),
                        true);
            }
        }
        return new ResponseErrorTemplate(
                "Success",
                String.valueOf(HttpStatus.OK),
                new EmptyObject(),
                false);
    }

    public User mapUserRequestToUser(final CreateUserRequestDto userRequest,
                                     User user) {
        user.setUsername(userRequest.getUsername());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setUserType(userRequest.getUserImg());
        user.setUserType(userRequest.getUserType());
        user.setPassword(passwordEncoder.encode(userRequest.getPassword()));
        user.setEmail(userRequest.getEmail());
        user.setGender(userRequest.getGender());
        user.setDateOfBirth(userRequest.getDateOfBirth());
        user.setLoginAttempt(Optional.ofNullable(userRequest.getLoginAttempt()).orElse(0));

        return user;
    }

    public UserResponse mapUserToUserResponse(final User user) {
        return new UserResponse(
                user.getId(),
                user.getUsername(),
                user.getFirstName(),
                user.getLastName(),
                user.getUserImage(),
                user.getGender(),
                user.getDateOfBirth(),
                user.getPassword(),
                user.getEmail(),
                user.getFirstName() + " " + user.getLastName(),
                user.getCreatedAt(),
                user.getLastLogin(),
                user.getLoginAttempt(),
                user.getMaxAttempt(),
                user.getStatus(),
                user.getRoles().stream().map(Role::getName).toList()
        );
    }
}

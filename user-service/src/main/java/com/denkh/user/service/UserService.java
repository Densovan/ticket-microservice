package com.denkh.user.service;

import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.user.dto.request.CreateUserRequestDto;
import com.denkh.user.dto.request.UserChangePasswordRequest;
import com.denkh.user.dto.request.UserFilterRequest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Set;


public interface UserService {

    ResponseErrorTemplate create(CreateUserRequestDto userRequest);

    ResponseErrorTemplate update(Long id, CreateUserRequestDto userRequest);

    ResponseErrorTemplate findById(Long id);

    @Transactional(readOnly = true)
    ResponseErrorTemplate findAll(UserFilterRequest filterRequest);

    ResponseErrorTemplate findByUsername(String username);

    ResponseErrorTemplate changePassword(Long id, UserChangePasswordRequest userChangePasswordRequest);

    ResponseErrorTemplate disActivateUser(Set<Long> ids, String status);

    ResponseErrorTemplate resetPassword(Set<Long> ids);

}

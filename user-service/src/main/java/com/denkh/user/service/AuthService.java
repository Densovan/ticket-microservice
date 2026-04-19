package com.denkh.user.service;

import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.user.dto.request.AuthenticationRequest;

public interface AuthService {

    ResponseErrorTemplate login(AuthenticationRequest authenticationRequest);
}
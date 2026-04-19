package com.denkh.user.service.impl;

import com.denkh.common.constant.ApiConstant;
import com.denkh.common.dto.EmptyObject;
import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.user.dto.request.AuthenticationRequest;
import com.denkh.user.dto.response.AuthenticationResponse;
import com.denkh.user.entity.CustomUserDetail;
import com.denkh.user.service.AuthService;
import com.denkh.user.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final CustomUserDetailService customUserDetailService;

    @Override
    public ResponseErrorTemplate login(AuthenticationRequest authenticationRequest){
        final String username = authenticationRequest.username();
        final String password = authenticationRequest.password();

        CustomUserDetail customUserDetail = customUserDetailService.customUserDetail(username);

        if(!StringUtils.hasText(password)){
            log.error("Password is empty for user: {}", username);
            return new ResponseErrorTemplate(
                    "Password cannot be empty",
                    ApiConstant.INVALID_REQUEST.getKey(),
                    new EmptyObject(),
                    true
            );
        }

        if(!passwordEncoder.matches(password, customUserDetail.getPassword())){
            log.error("Invalid password for user: {}", username);
            customUserDetailService.saveUserAttemptAuthentication(username);
            return new ResponseErrorTemplate(
                    "Invalid password",
                    ApiConstant.FORBIDDEN.getKey(),
                    new EmptyObject(),
                    true
            );
        }

        return new ResponseErrorTemplate(
                ApiConstant.LOGIN_SUCCESS.getDescription(),
                ApiConstant.LOGIN_SUCCESS.getKey(),
                new AuthenticationResponse(
                        jwtService.generateToken(customUserDetail),
                        jwtService.refreshToken(customUserDetail)),
                false);
    }

}

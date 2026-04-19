package com.denkh.user.service;



import com.denkh.common.exception.ResponseErrorTemplate;
import com.denkh.user.entity.CustomUserDetail;
import io.jsonwebtoken.Claims;
import org.springframework.stereotype.Service;

import java.security.Key;


public interface JwtService {

    Claims extractClaims(String token);
    Key getKey();
    String generateToken(CustomUserDetail customUserDetail);
    String refreshToken(CustomUserDetail customUserDetail);
    boolean isValidToken(String token);
    ResponseErrorTemplate verifyToken(String authorizationHeader);

}

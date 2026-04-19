package com.denkh.user.dto.request;

public record AuthenticationRequest(
        String username,
        String password){}
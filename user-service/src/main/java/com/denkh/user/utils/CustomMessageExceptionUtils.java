package com.denkh.user.utils;


import com.denkh.common.dto.EmptyObject;
import com.denkh.common.exception.CustomMessageException;
import org.springframework.http.HttpStatus;

public class CustomMessageExceptionUtils {

    private CustomMessageExceptionUtils() {}

    public static CustomMessageException unauthorized() {

        CustomMessageException messageException = new CustomMessageException();
        messageException.setMessage("Unauthorized");
        messageException.setCode(String.valueOf(HttpStatus.UNAUTHORIZED.value()));
        messageException.setObject(new EmptyObject());
        messageException.setHttpStatus(HttpStatus.UNAUTHORIZED);
        return messageException;
    }

}
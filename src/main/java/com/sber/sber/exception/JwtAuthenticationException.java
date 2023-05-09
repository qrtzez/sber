package com.sber.sber.exception;

import org.springframework.security.core.AuthenticationException;

public class JwtAuthenticationException extends AuthenticationException {

    public JwtAuthenticationException(ErrorMessages messages) {
        super(messages.getDescription());
    }

    public JwtAuthenticationException(String msg) {
        super(msg);
    }
}

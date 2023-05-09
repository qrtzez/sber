package com.sber.sber.exception;

public class RegistrationException extends RuntimeException {

    public RegistrationException(ErrorMessages message) {
        super(message.getDescription());
    }
}

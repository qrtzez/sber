package com.sber.sber.exception;

public class NotFoundException extends RuntimeException {

    public NotFoundException(ErrorMessages message) {
        super(message.getDescription());
    }
}

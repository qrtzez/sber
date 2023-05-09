package com.sber.sber.exception;

public class MoneyException extends RuntimeException {

    public MoneyException(ErrorMessages message) {
        super(message.getDescription());
    }
}

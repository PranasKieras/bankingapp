package com.banking.app.exception;

import org.springframework.http.HttpStatus;

public class InsufficientFundsException extends Exception {

    private static final String MESSAGE = "not enough money on account to perform this operation";

    public InsufficientFundsException() {
        super(MESSAGE);
    }
}

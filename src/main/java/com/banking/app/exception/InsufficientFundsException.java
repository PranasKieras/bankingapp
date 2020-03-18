package com.banking.app.exception;

public class InsufficientFundsException extends Exception {

    private static final String MESSAGE = "not enough money on account to perform this operation";

    public InsufficientFundsException() {
        super(MESSAGE);
    }
}

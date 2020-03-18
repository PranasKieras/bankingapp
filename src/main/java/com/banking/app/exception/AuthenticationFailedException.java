package com.banking.app.exception;

public class AuthenticationFailedException extends Exception {

    private static final String MESSAGE = "authentication failed";

    public AuthenticationFailedException() {
        super(MESSAGE);
    }
}

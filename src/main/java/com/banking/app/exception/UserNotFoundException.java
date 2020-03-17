package com.banking.app.exception;

public class UserNotFoundException extends Exception {

    private static final String MESSAGE = "a user with such an email was not found";
    private static final int CODE = 404;

    public UserNotFoundException() {
        super(MESSAGE);
    }
}

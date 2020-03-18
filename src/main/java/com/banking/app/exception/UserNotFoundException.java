package com.banking.app.exception;

import org.springframework.http.HttpStatus;

public class UserNotFoundException extends Exception {

    private static final String MESSAGE = "a user with such an email was not found";

    public UserNotFoundException() {
        super(MESSAGE);
    }
}

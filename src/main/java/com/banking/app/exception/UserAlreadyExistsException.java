package com.banking.app.exception;

import org.springframework.http.HttpStatus;

public class UserAlreadyExistsException extends Exception {

    private static final String MESSAGE = "a user with a given email already exists";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }
}

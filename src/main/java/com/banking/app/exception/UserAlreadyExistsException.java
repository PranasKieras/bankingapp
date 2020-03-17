package com.banking.app.exception;

public class UserAlreadyExistsException extends Exception {

    private static final String MESSAGE = "a user with a given email already exists";

    public UserAlreadyExistsException() {
        super(MESSAGE);
    }
}

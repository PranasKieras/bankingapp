package com.banking.app.exception;

public class UserAlreadyExistsException extends BankingException{

    private static final int CODE = 400;

    public UserAlreadyExistsException() {
        super("a user with a given email already exists", CODE);
    }
}

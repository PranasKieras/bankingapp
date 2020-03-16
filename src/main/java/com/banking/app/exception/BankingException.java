package com.banking.app.exception;

public class BankingException extends Exception{

    private final int code;

    BankingException(String message, int code){
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return String.format("{\n" +
               "\t\"code\" : \"%s\",\n" +
               "\t\"code\" : \"%s\",\n" +
               "}", code, super.getMessage());
    }
}

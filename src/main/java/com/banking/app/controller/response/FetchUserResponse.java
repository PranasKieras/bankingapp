package com.banking.app.controller.response;

import java.io.Serializable;

public class FetchUserResponse implements Serializable {

    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

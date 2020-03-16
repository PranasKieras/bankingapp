package com.banking.app.controller.request;


import javax.validation.constraints.Email;
import java.io.Serializable;

public class FetchUserRequest implements Serializable {

    @Email
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

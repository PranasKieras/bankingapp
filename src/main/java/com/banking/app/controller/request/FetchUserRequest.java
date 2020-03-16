package com.banking.app.controller.request;


import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

public class FetchUserRequest implements Serializable {

    @Email
    @NotEmpty(message = "email is a mandatory field")
    private String email;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}

package com.banking.app.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.io.Serializable;

public class AuthenticatedRequest implements Serializable {
    @Email(message = "needs to be of valid email format")
    @NotEmpty(message = "email is a mandatory field")
    @Size(min = 5, max=100,  message = "email must be between 5 and 100 characters")
    private String email;

    @NotEmpty(message = "password is a mandatory field")
    @Size(min=8, max=100, message = "mandatory field min length 8 max length 100 characters")
    private String password;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}

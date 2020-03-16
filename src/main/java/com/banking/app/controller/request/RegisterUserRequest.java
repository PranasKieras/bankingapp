package com.banking.app.controller.request;

import javax.validation.constraints.Email;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import java.io.Serializable;

public class RegisterUserRequest implements Serializable {

    @Email(message = "needs to be of valid email format")
    @NotBlank(message = "mandatory field")
    private String email;

    @Min(value=8, message = "mandatory field min length 8 characters")
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

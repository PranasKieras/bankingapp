package com.banking.app.service;

import com.banking.app.controller.request.RegisterUserRequest;

public interface RegisterUserService {

    void registerUser(RegisterUserRequest signUpRequest);
}

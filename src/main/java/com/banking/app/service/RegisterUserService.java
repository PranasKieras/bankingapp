package com.banking.app.service;

import com.banking.app.controller.request.RegisterUserRequest;
import com.banking.app.exception.UserAlreadyExistsException;

public interface RegisterUserService {

    void registerUser(RegisterUserRequest signUpRequest) throws UserAlreadyExistsException;
}

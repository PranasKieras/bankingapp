package com.banking.app.service;

import com.banking.app.controller.request.AuthenticatedRequest;
import com.banking.app.exception.UserAlreadyExistsException;
import org.springframework.stereotype.Service;

@Service
public interface RegisterUserService {

    void registerUser(AuthenticatedRequest registerUserRequest) throws UserAlreadyExistsException;
}

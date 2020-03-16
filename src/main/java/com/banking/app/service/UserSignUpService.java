package com.banking.app.service;

import com.banking.app.controller.request.UserSignUpRequest;

public interface UserSignUpService {

    void signUpUser(UserSignUpRequest signUpRequest);
}

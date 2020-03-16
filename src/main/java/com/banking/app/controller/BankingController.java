package com.banking.app.controller;

import com.banking.app.controller.request.UserSignUpRequest;
import com.banking.app.service.UserSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/api")
public class BankingController {

    @Autowired
    UserSignUpService userSignUpService;

    @PostMapping("/user")
    public void signUpUser(UserSignUpRequest signUpUserRequest) {
        userSignUpService.signUpUser(signUpUserRequest);
    }
}

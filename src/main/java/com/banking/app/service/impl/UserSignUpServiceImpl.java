package com.banking.app.service.impl;

import com.banking.app.controller.request.UserSignUpRequest;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.User;
import com.banking.app.service.UserSignUpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserSignUpServiceImpl implements UserSignUpService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void signUpUser(UserSignUpRequest signUpRequest) {
        User user = new User("email", "password");
        userRepository.save(user);
    }
}

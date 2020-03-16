package com.banking.app.service.impl;

import com.banking.app.controller.request.RegisterUserRequest;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.User;
import com.banking.app.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void registerUser(RegisterUserRequest signUpRequest) {
        User user = new User();
        user.setEmail(signUpRequest.getEmail());
        user.setPassword(signUpRequest.getPassword());
        userRepository.save(user);
    }
}

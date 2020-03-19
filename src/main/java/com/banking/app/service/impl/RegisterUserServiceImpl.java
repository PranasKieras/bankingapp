package com.banking.app.service.impl;

import com.banking.app.controller.request.AuthenticatedRequest;
import com.banking.app.exception.UserAlreadyExistsException;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.User;
import com.banking.app.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class RegisterUserServiceImpl implements RegisterUserService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void registerUser(AuthenticatedRequest registerUserRequest) throws UserAlreadyExistsException {
        Optional<User> userOptional = userRepository.findByEmail(registerUserRequest.getEmail());
        if(userOptional.isPresent())
            throw new UserAlreadyExistsException();
        User user = new User();
        user.setEmail(registerUserRequest.getEmail());
        user.setPassword(registerUserRequest.getPassword());
        userRepository.save(user);
    }
}

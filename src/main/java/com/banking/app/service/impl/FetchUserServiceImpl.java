package com.banking.app.service.impl;

import com.banking.app.controller.request.FetchUserRequest;
import com.banking.app.controller.response.FetchUserResponse;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.User;
import com.banking.app.service.FetchUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class FetchUserServiceImpl implements FetchUserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<FetchUserResponse> fetchUser(FetchUserRequest fetchUserRequest) {
        return userRepository
                .findByEmail(fetchUserRequest.getEmail())
                .flatMap(this::processDbEntity);
    }

    private Optional<FetchUserResponse> processDbEntity(User user){
        FetchUserResponse response = new FetchUserResponse();
        response.setEmail(user.getEmail());
        response.setBalance(user.getBalance());
        return Optional.of(response);
    }
}

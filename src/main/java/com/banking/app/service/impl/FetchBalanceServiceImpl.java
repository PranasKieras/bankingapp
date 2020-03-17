package com.banking.app.service.impl;

import com.banking.app.controller.request.FetchBalanceRequest;
import com.banking.app.controller.response.FetchBalanceResponse;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.User;
import com.banking.app.service.FetchBalanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
class FetchBalanceServiceImpl implements FetchBalanceService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Optional<FetchBalanceResponse> fetchBalance(FetchBalanceRequest fetchBalanceRequest) {
        return userRepository
                .findByEmail(fetchBalanceRequest.getEmail())
                .flatMap(this::processDbEntity);
    }

    private Optional<FetchBalanceResponse> processDbEntity(User user){
        FetchBalanceResponse response = new FetchBalanceResponse();
        response.setEmail(user.getEmail());
        response.setBalance(user.getBalance());
        return Optional.of(response);
    }
}

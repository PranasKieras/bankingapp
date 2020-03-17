package com.banking.app.service.impl;

import com.banking.app.controller.request.DepositRequest;
import com.banking.app.exception.UserNotFoundException;
import com.banking.app.repository.AccountEventRepository;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.AccountEvent;
import com.banking.app.repository.entity.User;
import com.banking.app.service.DepositService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
class DepositServiceImpl implements DepositService {

    @Autowired
    private AccountEventRepository accountEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void deposit(DepositRequest depositRequest) throws UserNotFoundException {
        userRepository.findByEmail(depositRequest.getEmail())
                .map(user -> {
                    updateUser(depositRequest, user);
                    return user;
                }).orElseThrow(UserNotFoundException::new);
    }

    private void updateUser(DepositRequest depositRequest, User user) {
        user.setBalance(user.getBalance().add(depositRequest.getAmount()));
        userRepository.save(user);

        AccountEvent accountEvent = new AccountEvent();
        accountEvent.setAmount(depositRequest.getAmount());
        accountEvent.setUser(user);
        accountEventRepository.save(accountEvent);
    }
}

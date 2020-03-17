package com.banking.app.service.impl;

import com.banking.app.controller.request.DepositRequest;
import com.banking.app.controller.request.WithdrawalRequest;
import com.banking.app.exception.UserNotFoundException;
import com.banking.app.repository.AccountEventRepository;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.AccountEvent;
import com.banking.app.repository.entity.User;
import com.banking.app.service.WithdrawalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
class WithdrawalServiceImpl implements WithdrawalService {

    @Autowired
    private AccountEventRepository accountEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void withdraw(WithdrawalRequest withdrawalRequest) throws UserNotFoundException {
        userRepository.findByEmail(withdrawalRequest.getEmail())
                .map(user -> {
                    updateUser(withdrawalRequest, user);
                    return user;
                }).orElseThrow(UserNotFoundException::new);
    }

    private void updateUser(WithdrawalRequest depositRequest, User user) {
        user.setBalance(user.getBalance().subtract(depositRequest.getAmount()));
        userRepository.save(user);

        AccountEvent accountEvent = new AccountEvent();
        accountEvent.setAmount(depositRequest.getAmount().negate());
        accountEvent.setUser(user);
        accountEventRepository.save(accountEvent);
    }
}

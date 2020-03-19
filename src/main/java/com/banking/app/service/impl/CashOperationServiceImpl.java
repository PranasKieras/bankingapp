package com.banking.app.service.impl;

import com.banking.app.controller.request.CashOperationRequest;
import com.banking.app.exception.InsufficientFundsException;
import com.banking.app.exception.UserNotFoundException;
import com.banking.app.operation.Operation;
import com.banking.app.repository.AccountEventRepository;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.AccountEvent;
import com.banking.app.repository.entity.User;
import com.banking.app.service.CashOperationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;

@Service
@Transactional
class CashOperationServiceImpl implements CashOperationService {

    @Autowired
    private AccountEventRepository accountEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void perform(CashOperationRequest cashOperationRequest, Operation operation) throws UserNotFoundException, InsufficientFundsException {
        User user = userRepository
                .findByEmail(cashOperationRequest.getEmail())
                .orElseThrow(UserNotFoundException::new);

        validateBalance(operation, user.getBalance(), cashOperationRequest.getAmount());

        updateUser(operation, cashOperationRequest.getAmount(), user);

        saveAccountEvent(operation, cashOperationRequest.getAmount(), user);
    }

    private void validateBalance(Operation operation, BigDecimal balance, BigDecimal amount) throws InsufficientFundsException {
        if(Operation.WITHDRAWAL == operation
                && balance.compareTo(amount) < 0)
            throw new InsufficientFundsException();
    }

    private void updateUser(Operation operation, BigDecimal amount, User user) {
        BigDecimal newBalance = operation.apply(user.getBalance(), amount);
        user.setBalance(newBalance);
        userRepository.save(user);
    }

    private void saveAccountEvent(Operation operation, BigDecimal amount, User user){
        AccountEvent accountEvent = new AccountEvent();
        accountEvent.setAmount(amount);
        accountEvent.setOperation(operation);
        accountEvent.setBalanceAfterOperation(user.getBalance());
        accountEvent.setUser(user);
        accountEventRepository.save(accountEvent);
    }
}

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
import org.springframework.transaction.annotation.Isolation;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional
class CashOperationServiceImpl implements CashOperationService {

    @Autowired
    private AccountEventRepository accountEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void perform(CashOperationRequest cashOperationRequest, Operation operation) throws UserNotFoundException, InsufficientFundsException {
        Optional<User> userOptional = userRepository.findByEmail(cashOperationRequest.getEmail());
        userOptional
                .orElseThrow(UserNotFoundException::new);

        userOptional
                .filter(user -> isValidEndBalance(operation, user.getBalance(), cashOperationRequest.getAmount()))
                .orElseThrow(InsufficientFundsException::new);

        userOptional
                .map(user -> performOperation(operation, cashOperationRequest.getAmount(), user));
    }

    private User performOperation(Operation operation, BigDecimal amount, User user){
        updateUser(operation, amount, user);
        writeAccountEvent(operation, amount, user);

        return user;
    }

    private boolean isValidEndBalance(Operation operation, BigDecimal balance, BigDecimal amount) {
        return Operation.DEPOSIT == operation
                || balance.compareTo(amount) >= 0;
    }

    private void updateUser(Operation operation, BigDecimal amount, User user) {
        BigDecimal newBalance = operation.apply(user.getBalance(), amount);
        user.setBalance(newBalance);
        userRepository.save(user);
    }

    private void writeAccountEvent(Operation operation, BigDecimal amount, User user){
        AccountEvent accountEvent = new AccountEvent();
        accountEvent.setAmount(amount);
        accountEvent.setOperation(operation);
        accountEvent.setUser(user);
        accountEventRepository.save(accountEvent);
    }
}

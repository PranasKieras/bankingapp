package com.banking.app.service;

import com.banking.app.controller.request.CashOperationRequest;
import com.banking.app.exception.InsufficientFundsException;
import com.banking.app.exception.UserNotFoundException;
import com.banking.app.operation.Operation;

import javax.naming.InterruptedNamingException;

public interface CashOperationService {

    void perform(CashOperationRequest depositRequest, Operation operation) throws UserNotFoundException, InsufficientFundsException;
}

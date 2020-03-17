package com.banking.app.service;

import com.banking.app.controller.request.DepositRequest;
import com.banking.app.exception.UserNotFoundException;

public interface DepositService {

    void deposit(DepositRequest depositRequest) throws UserNotFoundException;
}

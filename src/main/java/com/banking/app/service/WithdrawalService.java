package com.banking.app.service;

import com.banking.app.controller.request.WithdrawalRequest;
import com.banking.app.exception.UserNotFoundException;

public interface WithdrawalService {

    void withdraw(WithdrawalRequest withdrawalRequest) throws UserNotFoundException;
}

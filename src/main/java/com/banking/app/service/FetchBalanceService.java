package com.banking.app.service;

import com.banking.app.controller.request.AuthenticatedRequest;
import com.banking.app.controller.response.FetchBalanceResponse;
import com.banking.app.exception.UserNotFoundException;

public interface FetchBalanceService {

    FetchBalanceResponse fetchBalance(AuthenticatedRequest email) throws UserNotFoundException;

}

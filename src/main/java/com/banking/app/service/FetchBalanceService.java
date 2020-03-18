package com.banking.app.service;

import com.banking.app.controller.request.FetchBalanceRequest;
import com.banking.app.controller.response.FetchBalanceResponse;
import com.banking.app.exception.UserNotFoundException;

public interface FetchBalanceService {

    FetchBalanceResponse fetchBalance(FetchBalanceRequest email) throws UserNotFoundException;

}

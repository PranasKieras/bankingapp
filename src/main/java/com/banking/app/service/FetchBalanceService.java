package com.banking.app.service;

import com.banking.app.controller.request.FetchBalanceRequest;
import com.banking.app.controller.response.FetchBalanceResponse;

import java.util.Optional;

public interface FetchBalanceService {

    Optional<FetchBalanceResponse> fetchBalance(FetchBalanceRequest email);

}

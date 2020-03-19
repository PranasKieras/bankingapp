package com.banking.app.service.impl;

import com.banking.app.controller.request.AuthenticatedRequest;
import com.banking.app.controller.response.AccountEventResponse;
import com.banking.app.controller.response.FetchStatementResponse;
import com.banking.app.exception.UserNotFoundException;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.AccountEvent;
import com.banking.app.repository.entity.User;
import com.banking.app.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;

import static java.util.stream.Collectors.toCollection;

@Service
class StatementServiceImpl implements StatementService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public FetchStatementResponse fetchStatement(AuthenticatedRequest fetchStatementRequest) throws UserNotFoundException {
        return userRepository
                .findByEmail(fetchStatementRequest.getEmail())
                .map(this::getFetchStatementResponse)
                .orElseThrow(UserNotFoundException::new);
    }

    private FetchStatementResponse getFetchStatementResponse(User user) {
        FetchStatementResponse response = new FetchStatementResponse();
        response.setStatement(getAccountEventResponses(user));
        return response;
    }

    private LinkedHashSet<AccountEventResponse> getAccountEventResponses(User user) {
        return user.getAccountEvents()
                .stream()
                .map(this::getAccountEventResponse)
                .collect(toCollection(LinkedHashSet::new));
    }

    private AccountEventResponse getAccountEventResponse(AccountEvent event) {
        AccountEventResponse eventResponse = new AccountEventResponse();
        eventResponse.setAmount(event.getAmount());
        eventResponse.setOperation(event.getOperation());
        eventResponse.setBalanceAfterOperation(event.getBalanceAfterOperation());
        eventResponse.setCreationDate(event.getCreationDate());
        return eventResponse;
    }
}

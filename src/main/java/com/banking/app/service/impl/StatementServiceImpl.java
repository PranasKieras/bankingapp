package com.banking.app.service.impl;

import com.banking.app.controller.request.FetchStatementRequest;
import com.banking.app.controller.response.AccountEventResponse;
import com.banking.app.controller.response.FetchStatementResponse;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.User;
import com.banking.app.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

import static java.util.stream.Collectors.toSet;

@Service
class StatementServiceImpl implements StatementService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public FetchStatementResponse fetchStatement(FetchStatementRequest fetchStatementRequest) {
        Optional<User> user = userRepository
                .findByEmail(fetchStatementRequest.getEmail());
        Set<AccountEventResponse> events = user.get().getAccountEvents()
                .stream()
                .map(event -> {
                    AccountEventResponse eventResponse = new AccountEventResponse();
                    eventResponse.setAmount(event.getAmount());
                    eventResponse.setId(event.getId());
                    eventResponse.setOperation(event.getOperation());
                    return eventResponse;
                }).collect(toSet());
        FetchStatementResponse response = new FetchStatementResponse();
        response.setStatement(events);
        return response;
    }
}

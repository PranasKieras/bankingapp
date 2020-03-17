package com.banking.app.service.impl;

import com.banking.app.repository.AccountEventRepository;
import com.banking.app.repository.UserRepository;
import com.banking.app.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;

class StatementServiceImpl implements StatementService {


    @Autowired
    private AccountEventRepository accountEventRepository;

    @Autowired
    private UserRepository userRepository;

    @Override
    public void fetchStatement() {
        return;
    }
}

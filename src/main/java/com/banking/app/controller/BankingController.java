package com.banking.app.controller;

import com.banking.app.controller.request.CashOperationRequest;
import com.banking.app.controller.request.FetchBalanceRequest;
import com.banking.app.controller.request.FetchStatementRequest;
import com.banking.app.controller.request.RegisterUserRequest;
import com.banking.app.controller.response.FetchBalanceResponse;
import com.banking.app.controller.response.FetchStatementResponse;
import com.banking.app.exception.InsufficientFundsException;
import com.banking.app.exception.UserAlreadyExistsException;
import com.banking.app.exception.UserNotFoundException;
import com.banking.app.operation.Operation;
import com.banking.app.service.CashOperationService;
import com.banking.app.service.FetchBalanceService;
import com.banking.app.service.RegisterUserService;
import com.banking.app.service.StatementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.validation.Valid;

@Controller
@RequestMapping("/api")
public class BankingController {

    @Autowired
    private RegisterUserService registerUserService;

    @Autowired
    private FetchBalanceService fetchBalanceService;

    @Autowired
    private CashOperationService cashOperationService;

    @Autowired
    private StatementService statementService;

    @PostMapping("/user")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserRequest signUpUserRequest) throws UserAlreadyExistsException {
        registerUserService.registerUser(signUpUserRequest);
        return ResponseEntity.ok("{}");
    }

    @GetMapping("/user/balance")
    public ResponseEntity<FetchBalanceResponse> fetchBalance(@Valid @RequestBody FetchBalanceRequest fetchBalanceRequest) throws UserNotFoundException {
        return ResponseEntity.ok(fetchBalanceService
                .fetchBalance(fetchBalanceRequest));
    }

    @PostMapping("/user/deposit")
    public ResponseEntity<String> deposit(@Valid @RequestBody CashOperationRequest depositRequest) throws UserNotFoundException, InsufficientFundsException {
        cashOperationService.perform(depositRequest, Operation.DEPOSIT);
        return ResponseEntity.ok("{}");
    }

    @PostMapping("/user/withdraw")
    public ResponseEntity<String> withdraw(@Valid @RequestBody CashOperationRequest withdrawalRequest) throws UserNotFoundException, InsufficientFundsException {
        cashOperationService.perform(withdrawalRequest, Operation.WITHDRAWAL);
        return ResponseEntity.ok("{}");
    }

    @GetMapping("/user/statement")
    public ResponseEntity<FetchStatementResponse> fetchStatement(@Valid @RequestBody FetchStatementRequest fetchStatementRequest) {
        return ResponseEntity.ok(statementService
                .fetchStatement(fetchStatementRequest));
    }
}

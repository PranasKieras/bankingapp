package com.banking.app.controller;

import com.banking.app.controller.request.DepositRequest;
import com.banking.app.controller.request.FetchBalanceRequest;
import com.banking.app.controller.request.RegisterUserRequest;
import com.banking.app.controller.request.WithdrawalRequest;
import com.banking.app.controller.response.FetchBalanceResponse;
import com.banking.app.exception.UserAlreadyExistsException;
import com.banking.app.exception.UserNotFoundException;
import com.banking.app.service.DepositService;
import com.banking.app.service.FetchBalanceService;
import com.banking.app.service.RegisterUserService;
import com.banking.app.service.WithdrawalService;
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
    private DepositService depositService;

    @Autowired
    private WithdrawalService withdrawalService;

    @PostMapping("/user")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserRequest signUpUserRequest){
        ResponseEntity<String> response;
        try {
            registerUserService.registerUser(signUpUserRequest);
            response = ResponseEntity.ok("{}");
        } catch(UserAlreadyExistsException e){
            response = ResponseEntity.badRequest().body(e.getMessage());
        }
        return response;
    }

    @GetMapping("/user/balance")
    public ResponseEntity<FetchBalanceResponse> fetchBalance(@Valid @RequestBody FetchBalanceRequest fetchBalanceRequest) {
        return fetchBalanceService
                .fetchBalance(fetchBalanceRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/user/deposit")
    public ResponseEntity<String> deposit(@Valid @RequestBody DepositRequest depositRequest) {
        ResponseEntity<String> response = null;
        try {
            depositService.deposit(depositRequest);
            response = ResponseEntity.ok("{}");
        } catch (UserNotFoundException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return response;
    }

    @PostMapping("/user/withdraw")
    public ResponseEntity<String> withdraw(@Valid @RequestBody WithdrawalRequest withdrawalRequest) {
        ResponseEntity<String> response = null;
        try {
            withdrawalService.withdraw(withdrawalRequest);
            response = ResponseEntity.ok("{}");
        } catch (UserNotFoundException e) {
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        }
        return response;
    }
}

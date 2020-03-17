package com.banking.app.controller;

import com.banking.app.controller.request.FetchUserRequest;
import com.banking.app.controller.request.RegisterUserRequest;
import com.banking.app.controller.response.FetchUserResponse;
import com.banking.app.exception.UserAlreadyExistsException;
import com.banking.app.service.FetchUserService;
import com.banking.app.service.RegisterUserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    private FetchUserService fetchUserService;

    @PostMapping("/user")
    public ResponseEntity<String> registerUser(@Valid @RequestBody RegisterUserRequest signUpUserRequest){
        ResponseEntity<String> response;
        try {
            registerUserService.registerUser(signUpUserRequest);
            response = ResponseEntity.ok("{}");
        } catch(UserAlreadyExistsException e){
            response = ResponseEntity.badRequest().body(e.toString());
        }
        return response;
    }

    @GetMapping("/user")
    public ResponseEntity<FetchUserResponse> fetchUser(@Valid @RequestBody FetchUserRequest fetchUserRequest) {
        return fetchUserService
                .fetchUser(fetchUserRequest)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
}

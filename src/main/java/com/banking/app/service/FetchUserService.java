package com.banking.app.service;

import com.banking.app.controller.request.FetchUserRequest;
import com.banking.app.controller.response.FetchUserResponse;

import java.util.Optional;

public interface FetchUserService {

    Optional<FetchUserResponse> fetchUser(FetchUserRequest email);

}

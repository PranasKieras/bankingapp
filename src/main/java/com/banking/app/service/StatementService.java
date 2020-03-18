package com.banking.app.service;

import com.banking.app.controller.request.AuthenticatedRequest;
import com.banking.app.controller.response.FetchStatementResponse;
import com.banking.app.exception.UserNotFoundException;

public interface StatementService {

    FetchStatementResponse fetchStatement(AuthenticatedRequest fetchStatementRequest) throws UserNotFoundException;
}

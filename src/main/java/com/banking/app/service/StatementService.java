package com.banking.app.service;

import com.banking.app.controller.request.FetchStatementRequest;
import com.banking.app.controller.response.FetchStatementResponse;

public interface StatementService {

    FetchStatementResponse fetchStatement(FetchStatementRequest fetchStatementRequest);
}

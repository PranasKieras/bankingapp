package com.banking.app.controller

import com.banking.app.controller.request.AuthenticatedRequest
import com.banking.app.controller.request.CashOperationRequest


import com.banking.app.controller.response.FetchBalanceResponse
import com.banking.app.controller.response.FetchStatementResponse
import com.banking.app.operation.Operation
import com.banking.app.service.CashOperationService
import com.banking.app.service.FetchBalanceService
import com.banking.app.service.RegisterUserService
import com.banking.app.service.StatementService
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class BankingControllerSpec extends Specification {

    def controller = new BankingController()

    RegisterUserService registerUserService = Mock()

    FetchBalanceService fetchBalanceService = Mock()

    CashOperationService cashOperationService = Mock()

    StatementService statementService = Mock()

    def setup() {
        controller.registerUserService = registerUserService
        controller.fetchBalanceService = fetchBalanceService
        controller.cashOperationService = cashOperationService
        controller.statementService = statementService

    }

    def "registerUser calls registerUserService registerUserRequest"() {
        given: "authentication request"
        def registerUserRequest = new AuthenticatedRequest()
        when: "registerUser is called"
        def response = controller.registerUser(registerUserRequest)
        then: "registerUserService registerUser is called with authentication request"
        1 * registerUserService.registerUser(registerUserRequest)
        and: "response is returned with status ok and empty body"
        response == ResponseEntity.ok("{}")
    }

    def "fetchBalance returns user when it exists"() {
        given:
        def fetchBalanceResponse = new FetchBalanceResponse()
        def fetchBalanceRequest = new AuthenticatedRequest()
        and: "fetchBalanceService fetch valance returns response"
        fetchBalanceService.fetchBalance(fetchBalanceRequest) >> fetchBalanceResponse
        when: "fetchBalance is called"
        def response = controller.fetchBalance(fetchBalanceRequest)
        then: "response is ok and body is fetchBalanceResponse"
        response == ResponseEntity.ok(fetchBalanceResponse)
    }

    def "deposit calls cashOperationService"() {
        given: "deposit request"
        def depositRequest = new CashOperationRequest()
        when: "controller deposit is called"
        def response = controller.deposit(depositRequest)
        then: "cashOperationService perform is called once with deposit request and operation DEPOSIT"
        1 * cashOperationService.perform(depositRequest, Operation.DEPOSIT)
        and: "response is ok with empty body"
        response == ResponseEntity.ok("{}")
    }

    def "withdraw calls cashOperationService"() {
        given: "withdrawal request"
        def withdrawalRequest = new CashOperationRequest()
        when: "when controller.withdraw is called"
        def response = controller.withdraw(withdrawalRequest)
        then: "cashOperationService.perform is called once with withdrawal request and operation DEPOSIT"
        1 * cashOperationService.perform(withdrawalRequest, Operation.WITHDRAWAL)
        and: "response is ok with empty body"
        response == ResponseEntity.ok("{}")
    }

    def "fetchStatement return correct response"() {
        given:
        def fetchStatementRequest = new AuthenticatedRequest()
        def fetchStatementResponse = new FetchStatementResponse()
        and: "statementService fetchStatement returns fetchStatementResponse"
        statementService.fetchStatement(fetchStatementRequest) >> fetchStatementResponse
        when: "controller fetchStatement is called"
        def response = controller.fetchStatement(fetchStatementRequest)
        then: "statementService fetchStatement is called once with fetchStatement request"
        and: "response is ok with empty body"
        response == ResponseEntity.ok(fetchStatementResponse)
    }
}

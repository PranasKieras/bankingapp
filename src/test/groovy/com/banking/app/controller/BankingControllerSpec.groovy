package com.banking.app.controller

import com.banking.app.controller.request.CashOperationRequest
import com.banking.app.controller.request.FetchBalanceRequest
import com.banking.app.controller.request.FetchStatementRequest
import com.banking.app.controller.request.RegisterUserRequest
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
        controller.statementService = statementService;

    }

    def "registerUser calls registerUserService registerUserRequest"() {
        given:
        def registerUserRequest = new RegisterUserRequest()

        when:
        def response = controller.registerUser(registerUserRequest)

        then:
        1 * registerUserService.registerUser(registerUserRequest)
        response == ResponseEntity.ok("{}")
    }

    def "fetchBalance returns user when it exists"() {
        given:
        def fetchBalanceResponse = new FetchBalanceResponse()
        def fetchBalanceRequest = new FetchBalanceRequest()
        fetchBalanceService.fetchBalance(fetchBalanceRequest) >> fetchBalanceResponse

        when:
        def response = controller.fetchBalance(fetchBalanceRequest)

        then:
        response == ResponseEntity.ok(fetchBalanceResponse)
    }

    def "deposit calls cashOperationService"() {
        given:
        def depositRequest = new CashOperationRequest()

        when:
        def response = controller.deposit(depositRequest)

        then:
        1 * cashOperationService.perform(depositRequest, Operation.DEPOSIT)
        response == ResponseEntity.ok("{}")
    }

    def "withdraw calls cashOperationService"() {
        given:
        def cashOperationRequest = new CashOperationRequest()

        when:
        def response = controller.withdraw(cashOperationRequest)

        then:
        1 * cashOperationService.perform(cashOperationRequest, Operation.WITHDRAWAL)
        response == ResponseEntity.ok("{}")
    }

    def "fetchStatement return correct response"() {
        given:
        def fetchStatementRequest = new FetchStatementRequest()
        def fetchStatementResponse = new FetchStatementResponse()
        statementService.fetchStatement(fetchStatementRequest) >> fetchStatementResponse

        when:
        def response = controller.fetchStatement(fetchStatementRequest)

        then:
        response == ResponseEntity.ok(fetchStatementResponse)
    }
}

package com.banking.app.service.impl

import com.banking.app.controller.request.AuthenticatedRequest
import com.banking.app.controller.response.AccountEventResponse
import com.banking.app.exception.UserNotFoundException
import com.banking.app.operation.Operation
import com.banking.app.repository.UserRepository
import com.banking.app.repository.entity.AccountEvent
import com.banking.app.repository.entity.User
import spock.lang.Specification

import static java.math.BigDecimal.*

class StatementServiceImplSpec extends Specification {

    StatementServiceImpl statusService = new StatementServiceImpl()

    UserRepository userRepository = Mock()

    User user = Mock()

    AuthenticatedRequest fetchStatementRequest

    String email

    String password

    Set<AccountEvent> events


    void setup() {
        statusService.userRepository = userRepository
        email = "name@domain.com"
        password = "password"
        fetchStatementRequest = new AuthenticatedRequest()
        fetchStatementRequest.email = email
        fetchStatementRequest.password = password
        events = new LinkedHashSet<>()
    }

    def "fetchStatement returns a response"() {
        given: "user exists"
        userRepository.findByEmail(email) >> Optional.of(user)
        and: "events are empty"
        user.getAccountEvents() >> events
        when: "statusService fetchStatement is called"
        def statementResponse = statusService.fetchStatement(fetchStatementRequest)
        then: "a response is returned"
        statementResponse
        and: "event response is of size 0"
        statementResponse.getStatement().size() == 0

    }

    def "fetchStatement account event list maps to response event list"() {
        given: "user exists"
        userRepository.findByEmail(email) >> Optional.of(user)
        and: "there are 3 event on account"
        user.getAccountEvents() >> events
        events << createAccountEvents(TEN, Operation.DEPOSIT, TEN)
        events << createAccountEvents(ONE, Operation.WITHDRAWAL, new BigDecimal(9))
        events << createAccountEvents(new BigDecimal(12), Operation.DEPOSIT, new BigDecimal(21))

        when: "statusService fetchStatement is called"
        def statementResponse = statusService.fetchStatement(fetchStatementRequest)

        then: "returned event list size is 3"
        statementResponse.getStatement().size() == 3

        and: "insertion order is maintained"
        LinkedHashSet<AccountEventResponse> responseStatement = new LinkedHashSet<>()
        responseStatement << createAccountEventResponse(TEN, Operation.DEPOSIT, TEN)
        responseStatement << createAccountEventResponse(ONE, Operation.WITHDRAWAL, new BigDecimal(9))
        responseStatement << createAccountEventResponse(new BigDecimal(12), Operation.DEPOSIT, new BigDecimal(21))

        and: "returned list is as expected"
        responseStatement == statementResponse.getStatement()
    }

    def "fetchStatement throws UserNotFoundException when no user exists"() {
        given: "user does not exist"
        userRepository.findByEmail(email) >> Optional.empty()
        when: "statusService fetchStatement is called"
        statusService.fetchStatement(fetchStatementRequest)
        then: "UserNotFoundException is thrown"
        thrown UserNotFoundException
    }

    def createAccountEvents(BigDecimal amount, Operation type, BigDecimal balanceAfterTransaction){
        AccountEvent event = new AccountEvent()
        event.amount = amount
        event.operation = type
        event.balanceAfterOperation = balanceAfterTransaction
        return event
    }

    def createAccountEventResponse(BigDecimal amount, Operation type, BigDecimal balanceAfterOperation){
        AccountEventResponse event = new AccountEventResponse()
        event.amount = amount
        event.operation = type
        event.balanceAfterOperation = balanceAfterOperation
        return event
    }
}

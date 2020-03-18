package com.banking.app.service.impl

import com.banking.app.controller.request.AuthenticatedRequest
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
        createAccountEvents()
    }

    def "fetchStatement returns a response when user exists"() {
        given:
        user.getAccountEvents() >> events
        userRepository.findByEmail(email) >> Optional.of(user)
        when:
        def statementResponse = statusService.fetchStatement(fetchStatementRequest)
        then:
        statementResponse
    }

    def "fetchStatement account event list maps to response event list"() {
        given:
        user.getAccountEvents() >> events
        userRepository.findByEmail(email) >> Optional.of(user)
        when:
        def statementResponse = statusService.fetchStatement(fetchStatementRequest)
        then:
        statementResponse.getStatement().size() == 1
        and:
        def accountEvent = statementResponse.getStatement()[0]
        accountEvent.operation == Operation.DEPOSIT
        accountEvent.amount == TEN
    }

    def "fetchStatement throws UserNotFoundException when no user exists"() {
        given:
        user.getAccountEvents() >> events
        userRepository.findByEmail(email) >> Optional.empty()
        when:
        statusService.fetchStatement(fetchStatementRequest)
        then:
        thrown UserNotFoundException
    }

    def createAccountEvents(){
        events = new HashSet<>()

        AccountEvent event = new AccountEvent()
        event.amount = TEN
        event.operation = Operation.DEPOSIT
        event.user = user

        events.add(event)
    }

}

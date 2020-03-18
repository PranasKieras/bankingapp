package com.banking.app.service.impl

import com.banking.app.controller.request.CashOperationRequest
import com.banking.app.exception.InsufficientFundsException
import com.banking.app.exception.UserNotFoundException
import com.banking.app.operation.Operation
import com.banking.app.repository.AccountEventRepository
import com.banking.app.repository.UserRepository
import com.banking.app.repository.entity.AccountEvent
import com.banking.app.repository.entity.User
import spock.lang.Specification

import static java.math.BigDecimal.*

class CashOperationServiceImplSpec extends Specification {

    CashOperationServiceImpl cashOperationService = new CashOperationServiceImpl()

    UserRepository userRepository = Mock()

    AccountEventRepository accountEventRepository = Mock()

    CashOperationRequest depositRequest

    String email

    String password

    def setup() {
        cashOperationService.userRepository = userRepository
        cashOperationService.accountEventRepository = accountEventRepository
        depositRequest = new CashOperationRequest()
        email = "name@domain.com"
        password = "password"
        depositRequest.email = email
        depositRequest.password = password
        depositRequest.amount = TEN
    }

    def "deposit calls AccountEventRepository and UserRepository when data is valid"() {
        given:
        def user = createUser(email, password, ZERO)
        userRepository.findByEmail(email) >> Optional.of(user)

        when:
        cashOperationService.perform(depositRequest, Operation.DEPOSIT)

        then:
        1 * userRepository.save(createUser(email, password, TEN))
        1 * accountEventRepository.save(createAccountEvent(TEN, user, Operation.DEPOSIT))
    }

    def "deposit calls throws UserNotFoundException when user does not exist"() {
        given:
        userRepository.findByEmail(email) >> Optional.empty()

        when:
        cashOperationService.perform(depositRequest, Operation.DEPOSIT)

        then:
        thrown UserNotFoundException
    }

    def "withdrawal throws InsufficientFundsException amount larger than balance"() {
        given:
        def user = createUser(email, password, ONE)
        userRepository.findByEmail(email) >> Optional.of(user)
        when:
        cashOperationService.perform(depositRequest, Operation.WITHDRAWAL)

        then:
        thrown InsufficientFundsException
    }

    def "withdrawal is ok when amount same as balance"() {
        given:
        def user = createUser(email, password, TEN)
        userRepository.findByEmail(email) >> Optional.of(user)
        when:
        cashOperationService.perform(depositRequest, Operation.WITHDRAWAL)

        then:
        1 * userRepository.save(createUser(email, password, ZERO))
        1 * accountEventRepository.save(createAccountEvent(TEN, user, Operation.WITHDRAWAL))
    }

    def createUser(String email, String password, BigDecimal balance) {
        User user = new User()
        user.email = email
        user.password = password
        user.balance = balance
        return user
    }

    def createAccountEvent(BigDecimal amount, User user, Operation operation) {
        AccountEvent event = new AccountEvent()
        event.amount = amount
        event.user = user
        event.operation = operation
        return event
    }
}

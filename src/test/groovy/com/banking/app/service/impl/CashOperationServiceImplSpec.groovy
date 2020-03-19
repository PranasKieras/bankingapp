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
        given:"default setup"
        cashOperationService.userRepository = userRepository
        cashOperationService.accountEventRepository = accountEventRepository
        depositRequest = new CashOperationRequest()
        email = "name@domain.com"
        password = "password"
        depositRequest.email = email
        depositRequest.password = password
        depositRequest.amount = TEN
    }

    def "deposit is successful"() {
        given:"user exists"
        userRepository.findByEmail(email) >> Optional.of(createUser(email, password, ZERO))
        when: "cashOperationService DEPOSIT operation is called"
        cashOperationService.perform(depositRequest, Operation.DEPOSIT)
        then: "userRepository save is called once"
        1 * userRepository.save(createUser(email, password, TEN))
        and: "accountEventRepository save is called once"
        1 * accountEventRepository.save(createAccountEvent(TEN, createUser(email, password, TEN), Operation.DEPOSIT))
    }

    def "deposit throws exception"() {
        given: "user does not exists"
        userRepository.findByEmail(email) >> Optional.empty()
        when: "cashOperationService DEPOSIT is called"
        cashOperationService.perform(depositRequest, Operation.DEPOSIT)
        then: "UserNotFoundException is thrown"
        thrown UserNotFoundException
    }

    def "withdrawal is ok when balance is more than amount"() {
        given: "user exists and balance is 10.00"
        userRepository.findByEmail(email) >> Optional.of(createUser(email, password, TEN))
        and: "request amount is ONE"
        depositRequest.amount = ONE
        when: "cashOperationService WITHDRAWAL is called"
        cashOperationService.perform(depositRequest, Operation.WITHDRAWAL)
        then: "userRepository save is called once with balance 0.00"
        1 * userRepository.save(createUser(email, password, new BigDecimal(9)))
        and: "accountEventRepository save is called with type WITHDRAWAL and amount TEN"
        1 * accountEventRepository.save(createAccountEvent(ONE, createUser(email, password, new BigDecimal(9)), Operation.WITHDRAWAL))
    }

    def "withdrawal is ok when amount same as balance"() {
        given: "user exists and balance is 10.00"
        userRepository.findByEmail(email) >> Optional.of(createUser(email, password, TEN))
        and: "request amount is TEN"
        depositRequest.amount = TEN
        when: "cashOperationService WITHDRAWAL is called"
        cashOperationService.perform(depositRequest, Operation.WITHDRAWAL)
        then: "userRepository save is called once with balance 0.00"
        1 * userRepository.save(createUser(email, password, ZERO))
        and: "accountEventRepository save is called with type WITHDRAWAL and amount TEN"
        1 * accountEventRepository.save(createAccountEvent(TEN, createUser(email, password, ZERO), Operation.WITHDRAWAL))
    }

    def "withdrawal throws exception"() {
        given: "user exists and balance is 1.00"
        userRepository.findByEmail(email) >> Optional.of(createUser(email, password, ONE))
        and: "withdrawal amount is 10.00"
        depositRequest.amount = TEN
        when: "cashOperationService WITHDRAWAL is called"
        cashOperationService.perform(depositRequest, Operation.WITHDRAWAL)
        then: "InsufficientFundsException is throw"
        thrown InsufficientFundsException
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
        event.balanceAfterOperation = user.balance
        event.operation = operation
        return event
    }
}

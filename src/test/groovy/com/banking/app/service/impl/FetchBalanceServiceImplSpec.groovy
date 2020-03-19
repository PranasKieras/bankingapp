package com.banking.app.service.impl

import com.banking.app.controller.request.AuthenticatedRequest
import com.banking.app.exception.UserNotFoundException
import com.banking.app.repository.UserRepository
import com.banking.app.repository.entity.User
import spock.lang.Specification

import static java.math.BigDecimal.*

class FetchBalanceServiceImplSpec extends Specification {

    FetchBalanceServiceImpl userService = new FetchBalanceServiceImpl()

    UserRepository userRepository = Mock()

    String email

    String password

    AuthenticatedRequest fetchBalanceRequest

    void setup() {
        userService.userRepository = userRepository
        email = "name@domain.com"
        password = "password"
        fetchBalanceRequest = new AuthenticatedRequest()
        fetchBalanceRequest.email = email
        fetchBalanceRequest.password = password
    }

    def "fetchBalance returns response"() {
        given: "user exists with balance 10.00"
        userRepository.findByEmail(email) >> Optional.of(createUser(email, password, TEN))
        when: "userService fetchBalance is called"
        def userResponse = userService.fetchBalance(fetchBalanceRequest)
        then: "userResponse is not null"
        userResponse
        and: "provided email is same as response email"
        email == userResponse.getEmail()
        and: "returned balance is 10.00"
        TEN == userResponse.balance
    }

    def "fetchBalance throws exception"() {
        given: "user does not exists"
        userRepository.findByEmail(email) >> Optional.empty()
        when: "userService fetchBalance is called"
        userService.fetchBalance(fetchBalanceRequest)
        then: "UserNotFoundException is thrown"
        thrown UserNotFoundException
    }

    def createUser(String email, String password, BigDecimal balance) {
        def user = new User()
        user.email = email
        user.password = password
        user.balance = balance

        return user
    }
}

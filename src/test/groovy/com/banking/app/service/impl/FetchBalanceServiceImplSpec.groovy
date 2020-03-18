package com.banking.app.service.impl

import com.banking.app.controller.request.FetchBalanceRequest
import com.banking.app.exception.UserNotFoundException
import com.banking.app.repository.UserRepository
import com.banking.app.repository.entity.User
import spock.lang.Specification

class FetchBalanceServiceImplSpec extends Specification {

    FetchBalanceServiceImpl userService = new FetchBalanceServiceImpl();

    UserRepository userRepository = Mock()

    String email

    String password

    FetchBalanceRequest fetchBalanceRequest

    void setup() {
        userService.userRepository = userRepository
        email = "name@domain.com";
        password = "password";
        fetchBalanceRequest = new FetchBalanceRequest()
        fetchBalanceRequest.email = email
        fetchBalanceRequest.password = password
    }

    def "fetchBalance returns response when user exists"() {
        given:
        userRepository.findByEmail(email) >> Optional.of(createUser(email, password))
        when:
        def userResponse = userService.fetchBalance(fetchBalanceRequest)
        then:
        userResponse
        email == userResponse.getEmail()
    }

    def "fetchBalance throws UserNotFoundException when user does not exist"() {
        given:
        userRepository.findByEmail(email) >> Optional.empty()
        when:
        userService.fetchBalance(fetchBalanceRequest)
        then:
        thrown UserNotFoundException
    }

    def createUser(String email, String password) {
        def user = new User()
        user.email = email
        user.password = password

        return user
    }
}

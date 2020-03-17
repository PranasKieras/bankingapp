package com.banking.app.service.impl

import com.banking.app.controller.request.FetchBalanceRequest
import com.banking.app.repository.UserRepository
import com.banking.app.repository.entity.User
import spock.lang.Specification

class FetchBalanceServiceImplSpec extends Specification {

    FetchBalanceServiceImpl userService = new FetchBalanceServiceImpl();

    UserRepository userRepository = Mock()

    void setup() {
        userService.userRepository = userRepository
    }

    def "fetch user returns user optional when it exists"() {
        given:
            def email = "name@domain.com";
            def password = "password";
            def fetchBalanceRequest = new FetchBalanceRequest()
            fetchBalanceRequest.email = email
            userRepository.findByEmail(email) >> Optional.of(createUser(email, password))
        when:
            def userResponse = userService.fetchBalance(fetchBalanceRequest)
        then:
            userResponse.isPresent()
            email == userResponse.get().getEmail()

    }

    def createUser(String email, String password){
        def user = new User()
        user.email = email
        user.password = password

        return user
    }
}

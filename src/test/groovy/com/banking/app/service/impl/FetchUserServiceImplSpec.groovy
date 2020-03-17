package com.banking.app.service.impl

import com.banking.app.controller.request.FetchUserRequest
import com.banking.app.repository.UserRepository
import com.banking.app.repository.entity.User
import spock.lang.Specification

class FetchUserServiceImplSpec extends Specification {

    FetchUserServiceImpl userService = new FetchUserServiceImpl();

    UserRepository userRepository = Mock()

    void setup() {
        userService.userRepository = userRepository
    }

    def "fetch user returns user optional when it exists"() {
        given:
            def email = "name@domain.com";
            def password = "password";
            def fetchUserRequest = new FetchUserRequest()
            fetchUserRequest.email = email
            userRepository.findByEmail(email) >> Optional.of(createUser(email, password))
        when:
            def userResponse = userService.fetchUser(fetchUserRequest)
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

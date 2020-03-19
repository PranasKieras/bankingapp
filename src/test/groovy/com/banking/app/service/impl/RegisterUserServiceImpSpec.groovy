package com.banking.app.service.impl

import com.banking.app.controller.request.AuthenticatedRequest
import com.banking.app.exception.UserAlreadyExistsException
import com.banking.app.repository.UserRepository
import com.banking.app.repository.entity.User
import spock.lang.Specification

import static java.math.BigDecimal.ZERO

class RegisterUserServiceImpSpec extends Specification {

    RegisterUserServiceImpl registerUserService = new RegisterUserServiceImpl()

    UserRepository userRepository = Mock()

    String email

    String password

    def setup() {
        registerUserService.userRepository = userRepository
        email = "email"
        password = "password"
    }

    def "registerUser calls userRepository with correct user data"() {
        given: "user request"
        def registerUserRequest = createRequest(email, password)
        and: "user does not exist"
        userRepository.findByEmail(email) >> Optional.empty()
        when: "registerUserService registerUser is called"
        registerUserService.registerUser(registerUserRequest)
        then: "userRepository save is called once with same email, password and balance of 0.00"
        1 * userRepository.save(createUser(email, password, ZERO))
    }

    def "registerUser throws exception"() {
        given: "user already exists"
        userRepository.findByEmail(email) >> Optional.of(createUser(email, password, ZERO))
        when: "registerUserService registerUser is called"
        registerUserService.registerUser(createRequest(email, password))
        then: "UserAlreadyExistsException is thrown"
        thrown UserAlreadyExistsException
    }

    def createRequest(String email, String password){
        AuthenticatedRequest registerUserRequest = new AuthenticatedRequest()
        registerUserRequest.email = email
        registerUserRequest.password = password
        return registerUserRequest
    }

    def createUser(String email, String password, BigDecimal balance){
        User user = new User()
        user.email = email
        user.password = password
        user.balance = balance
        return user
    }
}

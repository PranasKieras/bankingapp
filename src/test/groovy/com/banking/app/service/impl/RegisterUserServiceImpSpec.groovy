package com.banking.app.service.impl

import com.banking.app.controller.request.RegisterUserRequest
import com.banking.app.exception.UserAlreadyExistsException
import com.banking.app.repository.UserRepository
import com.banking.app.repository.entity.User
import org.springframework.dao.DataIntegrityViolationException
import spock.lang.Specification

class RegisterUserServiceImpSpec extends Specification {

    RegisterUserServiceImpl registerUserService = new RegisterUserServiceImpl()

    UserRepository userRepository = Mock()

    DataIntegrityViolationException dataIntegrityViolationException = Mock()

    RegisterUserRequest signUpRequest;

    User user;

    String email

    String password

    RegisterUserRequest registerUserRequest;


    def setup() {
        registerUserService.userRepository = userRepository
        signUpRequest = new RegisterUserRequest()
        user = new User()
        registerUserRequest = new RegisterUserRequest()
        email = "email"
        password = "password"
        user.email = email
        user.password = password
        registerUserRequest.email = email
        registerUserRequest.password = password
    }

    def "registerUser calls userRepository with correct user data"() {
        when:
        registerUserService.registerUser(registerUserRequest);
        then:
        1 * userRepository.save(user)
    }

    def "registerUser throws UserAlreadyExistsException when user already exists"() {
        given:
        userRepository.save(user) >> { throw dataIntegrityViolationException }
        when:
        registerUserService.registerUser(registerUserRequest);
        then:
        thrown UserAlreadyExistsException
    }
}

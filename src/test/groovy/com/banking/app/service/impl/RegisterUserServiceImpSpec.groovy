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


    def setup(){
        registerUserService.userRepository = userRepository
        signUpRequest = new RegisterUserRequest()
        user = new User()
    }

    def "registerUser calls userRepository with correct user data"() {
        given:
            def email = "email"
            def password = "password"
            signUpRequest.email = email
            signUpRequest.password = password
            user.email = email
            user.password = password
        when:
            registerUserService.registerUser(signUpRequest);
        then:
            1*userRepository.save(user)
    }

    def "registerUser throws UserAlreadyExistsException when user already exists"() {
        given:
            def email = "email"
            def password = "password"
            signUpRequest.setEmail(email)
            signUpRequest.setPassword(password)
            userRepository.save(_) >> {throw dataIntegrityViolationException}
        when:
            registerUserService.registerUser(signUpRequest);
        then:
            thrown UserAlreadyExistsException
    }
}

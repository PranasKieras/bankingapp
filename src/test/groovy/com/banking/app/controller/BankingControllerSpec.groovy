package com.banking.app.controller

import com.banking.app.controller.request.DepositRequest
import com.banking.app.controller.request.FetchBalanceRequest
import com.banking.app.controller.request.RegisterUserRequest
import com.banking.app.controller.response.FetchBalanceResponse
import com.banking.app.exception.UserAlreadyExistsException
import com.banking.app.exception.UserNotFoundException
import com.banking.app.service.DepositService
import com.banking.app.service.FetchBalanceService
import com.banking.app.service.RegisterUserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification

import static org.springframework.http.ResponseEntity.badRequest

class BankingControllerSpec extends Specification {

    def controller = new BankingController()

    RegisterUserService registerUserService = Mock()

    FetchBalanceService fetchBalanceService = Mock()

    DepositService depositService = Mock()

    FetchBalanceRequest fetchBalanceRequest

    def setup() {
        controller.registerUserService = registerUserService
        controller.fetchBalanceService = fetchBalanceService
        controller.depositService = depositService

        fetchBalanceRequest = new FetchBalanceRequest()
    }

    def "registerUser calls registerUserService registerUserRequest"() {
        given:
        def registerUserRequest = new RegisterUserRequest()

        when:
        def response = controller.registerUser(registerUserRequest)

        then:
        1 * registerUserService.registerUser(registerUserRequest)
        response == ResponseEntity.ok("{}")
    }

    def "registerUser returns 400 response when user already registered"() {
        given:
        def registerUserRequest = new RegisterUserRequest()
        registerUserService.registerUser(registerUserRequest) >> { throw new UserAlreadyExistsException() }

        when:
        def response = controller.registerUser(registerUserRequest)

        then:
        response == ResponseEntity.badRequest().body("a user with a given email already exists")
    }

    def "fetchBalance returns user when it exists"() {
        given:
        def email = "name@domain.com"
        def serviceResponse = createFetchBalanceResponse(email)
        fetchBalanceRequest.email = email
        fetchBalanceService.fetchBalance(fetchBalanceRequest) >> Optional.of(serviceResponse)

        when:
        def response = controller.fetchBalance(fetchBalanceRequest)

        then:
        response == ResponseEntity.ok(serviceResponse)
    }

    def "fetchBalance returns 404 response when user not found"() {
        given:
        def email = "name@domain.com";
        fetchBalanceRequest.email = email
        fetchBalanceService.fetchBalance(fetchBalanceRequest) >> Optional.empty()

        when:
        def response = controller.fetchBalance(fetchBalanceRequest)

        then:
        response == ResponseEntity.notFound().build()
    }

    def "deposit calls depositService"() {
        given:
        def depositRequest = new DepositRequest()
        when:
        def response = controller.deposit(depositRequest)
        then:
        1 * depositService.deposit(depositRequest)
        response == ResponseEntity.ok("{}")
    }

    def "deposit returns 404 response when user not found"() {
        given:
        def depositRequest = new DepositRequest()
        depositService.deposit(depositRequest) >> { throw new UserNotFoundException() }

        when:
        def response = controller.deposit(depositRequest)

        then:
        response == ResponseEntity.status(HttpStatus.NOT_FOUND).body("a user with such an email was not found")
    }

    def createFetchBalanceResponse(String email) {
        def response = new FetchBalanceResponse()
        response.email = email
        return response
    }
}

package com.banking.app.controller

import com.banking.app.controller.request.FetchUserRequest
import com.banking.app.controller.request.RegisterUserRequest
import com.banking.app.controller.response.FetchUserResponse
import com.banking.app.service.FetchUserService
import com.banking.app.service.RegisterUserService
import org.springframework.http.ResponseEntity
import spock.lang.Specification

class BankingControllerSpec extends Specification {

    def controller = new BankingController()

    RegisterUserService registerUserService = Mock()

    FetchUserService fetchUserService = Mock()

    FetchUserRequest fetchUserRequest;

    def setup(){
        controller.registerUserService = registerUserService
        controller.fetchUserService = fetchUserService

        fetchUserRequest = new FetchUserRequest()
    }

    def "registerUser service is called with registerUserRequest"() {
        given:
            def registerUserRequest = new RegisterUserRequest()
        when:
            def response = controller.registerUser(registerUserRequest)
        then:
            1*registerUserService.registerUser(registerUserRequest)
            response == ResponseEntity.ok("{}")
    }

    def "fetchUser returns user when it exists"(){
        given:
            def email = "name@domain.com"
            def serviceResponse = createFetchUserResponse(email)
            fetchUserRequest.email = email
            fetchUserService.fetchUser(fetchUserRequest) >> Optional.of(serviceResponse)
        when:
            def response = controller.fetchUser(fetchUserRequest)
        then:
            response == ResponseEntity.ok(serviceResponse)
    }

    def "fetchUser returns 404 response when user not found"() {
        given:
            def email = "name@domain.com";
            fetchUserRequest.email = email
            fetchUserService.fetchUser(fetchUserRequest) >> Optional.empty()
        when:
            def response = controller.fetchUser(fetchUserRequest)
        then:
            response == ResponseEntity.notFound().build()
    }

    def createFetchUserResponse(String email){
        def response = new FetchUserResponse();
        response.email = email;
        return response;
    }
}

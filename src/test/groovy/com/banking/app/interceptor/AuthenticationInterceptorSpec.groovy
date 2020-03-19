package com.banking.app.interceptor

import com.banking.app.exception.AuthenticationFailedException
import spock.lang.Specification

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class AuthenticationInterceptorSpec extends Specification {

    AuthenticationInterceptor interceptor = new AuthenticationInterceptor()

    HttpServletRequest request = Mock()

    HttpServletResponse response = Mock()

    Object handler = Mock()


    def "preHandle returns true"() {
        given: "request path is for registration"
        request.getPathInfo() >> "/api/user"
        and: "request method is POST"
        request.getMethod() >> "POST"
        when:
        def returns = interceptor.preHandle(request, response, handler)
        then: "returns true"
        returns
    }

    def "preHandle returns true other paths"() {
        given: "not registration path"
        request.getPathInfo() >> "/api/user/deposit"
        and: "authenticated header is true"
        request.getHeader("authenticated") >> "true"
        when:
        def returns = interceptor.preHandle(request, response, handler)
        then: "returns true"
        returns
    }

    def "preHandle throws exception"() {
        given: "not registration path"
        request.getPathInfo() >> "/api/user/deposit"
        and: "authenticated header is false"
        request.getHeader("authenticated") >> "false"
        when:
        interceptor.preHandle(request, response, handler)
        then: "throws AuthenticationFailedException"
        thrown AuthenticationFailedException
    }
}

package com.banking.app.interceptor;

import com.banking.app.exception.AuthenticationFailedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        if (isCustomerRegistration(request))
            return true;
        String authenticated = request.getHeader("authenticated");
        validateAuthenticationInfo(authenticated);

        return true;
    }

    private boolean isCustomerRegistration(HttpServletRequest request) {
        return request.getPathInfo().equalsIgnoreCase("/api/user")
                && request.getMethod().equalsIgnoreCase("POST");
    }

    private void validateAuthenticationInfo(String authenticated) throws AuthenticationFailedException {
        if (!"true".equalsIgnoreCase(authenticated))
            throw new AuthenticationFailedException();
    }
}

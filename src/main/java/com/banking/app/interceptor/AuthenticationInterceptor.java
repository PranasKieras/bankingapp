package com.banking.app.interceptor;

import com.banking.app.exception.AuthenticationFailedException;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Component
public class AuthenticationInterceptor extends HandlerInterceptorAdapter {

    private static final String REGISTRATION_PATH = "/api/user";

    @Override
    public boolean preHandle(
            HttpServletRequest request,
            HttpServletResponse response,
            Object handler) throws Exception {
        if(request.getPathInfo().equalsIgnoreCase(REGISTRATION_PATH))
            return true;
        String authenticated = request.getHeader("authenticated");
        validateAuthenticationInfo(authenticated);


        return true;
    }

    private void validateAuthenticationInfo(String authenticated) throws AuthenticationFailedException {
        if(!"true".equalsIgnoreCase(authenticated))
            throw new AuthenticationFailedException();
    }
}

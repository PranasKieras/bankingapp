package com.banking.app.controller;

import com.banking.app.controller.request.UserSignUpRequest;
import com.banking.app.service.UserSignUpService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@RunWith(MockitoJUnitRunner.class)
public class BankingControllerTest {

    @InjectMocks
    private BankingController controller;

    @Mock
    private UserSignUpService userSignUpService;

    @Mock
    private UserSignUpRequest signUpRequest;

    @Test
    public void givenSignUpRequest_UserSignUpServiceIsCalled(){
        controller.signUpUser(signUpRequest);

        verify(userSignUpService, times(1)).signUpUser(signUpRequest);
    }

}
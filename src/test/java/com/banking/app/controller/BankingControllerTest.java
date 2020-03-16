package com.banking.app.controller;

import com.banking.app.controller.request.FetchUserRequest;
import com.banking.app.controller.request.RegisterUserRequest;
import com.banking.app.controller.response.FetchUserResponse;
import com.banking.app.service.FetchUserService;
import com.banking.app.service.RegisterUserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.*;


@RunWith(MockitoJUnitRunner.class)
public class BankingControllerTest {

    @InjectMocks
    private BankingController controller;

    @Mock
    private RegisterUserService userSignUpService;

    @Mock
    private FetchUserService fetchUserService;

    @Test
    public void givenSignUpRequest_RegisterUserServiceIsCalled(){
        RegisterUserRequest signUpRequest = new RegisterUserRequest();

        controller.registerUser(signUpRequest);

        verify(userSignUpService, times(1)).registerUser(signUpRequest);
    }

    @Test
    public void givenEmail_WhenFetchUser_ThenReturnUserResponse() throws Exception {
        String email = "name@domain.com";
        FetchUserRequest fetchUserRequest = new FetchUserRequest();
        fetchUserRequest.setEmail(email);
        when(fetchUserService.fetchUser(fetchUserRequest))
                .thenReturn(Optional.of(createFetchUserResponse(email)));

        ResponseEntity<FetchUserResponse> response = controller.fetchUser(fetchUserRequest);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(email, response.getBody().getEmail());
    }

    private FetchUserResponse createFetchUserResponse(String email){
        FetchUserResponse response = new FetchUserResponse();
        response.setEmail(email);

        return response;
    }

}
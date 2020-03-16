package com.banking.app.service.impl;

import com.banking.app.controller.request.FetchUserRequest;
import com.banking.app.controller.response.FetchUserResponse;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class FetchUserServiceImplTest {

    @InjectMocks
    private FetchUserServiceImpl userService;

    @Mock
    private UserRepository userRepository;

    @Test
    public void givenValidEmail_WhenFetchUser_ThenReturnUserResponse() {
        String email = "name@domain.com";
        String password = "password";
        FetchUserRequest fetchUserRequest = new FetchUserRequest();
        fetchUserRequest.setEmail(email);
        Optional<User> userOptional = Optional.of(createUser(email, password));

        when(userRepository.findByEmail(email)).thenReturn(userOptional);

        Optional<FetchUserResponse> userResponse = userService.fetchUser(fetchUserRequest);

        assertTrue(userResponse.isPresent());
        assertEquals(email, userResponse.get().getEmail());
    }


    private User createUser(String email, String password){
        User user = new User();
        user.setEmail(email);
        user.setPassword(password);

        return user;
    }
}
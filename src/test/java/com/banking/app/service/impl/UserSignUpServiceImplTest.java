package com.banking.app.service.impl;

import com.banking.app.controller.request.UserSignUpRequest;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

@RunWith(MockitoJUnitRunner.class)
public class UserSignUpServiceImplTest {

    @InjectMocks
    private UserSignUpServiceImpl userService;

    @Mock
    private UserSignUpRequest signUpRequest;

    final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    @Mock
    private UserRepository userRepository;

    @Test
    public void givenUsers_whenSignUpUser_thenCallUserRepository() {
        userService.signUpUser(signUpRequest);

        verify(userRepository, times(1)).save(captor.capture());
        assertNotNull(captor.getValue());
        assertEquals("email", captor.getValue().getEmail());
        assertEquals("password", captor.getValue().getPassword());
    }
}
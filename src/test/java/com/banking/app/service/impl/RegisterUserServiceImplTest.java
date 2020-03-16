package com.banking.app.service.impl;

import com.banking.app.controller.request.RegisterUserRequest;
import com.banking.app.exception.UserAlreadyExistsException;
import com.banking.app.repository.UserRepository;
import com.banking.app.repository.entity.User;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.dao.DataIntegrityViolationException;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class RegisterUserServiceImplTest {

    @InjectMocks
    private RegisterUserServiceImpl userService;

    final ArgumentCaptor<User> captor = ArgumentCaptor.forClass(User.class);

    @Mock
    private UserRepository userRepository;

    @Mock
    private DataIntegrityViolationException dataIntegrityViolationException;

    RegisterUserRequest signUpRequest;

    @Before
    public void setUp(){
        signUpRequest = new RegisterUserRequest();
    }

    @Test
    public void whenRegisterUser_ThenCallUserRepository() throws UserAlreadyExistsException {
        String email = "email";
        String password = "password";
        signUpRequest.setEmail(email);
        signUpRequest.setPassword(password);

        userService.registerUser(signUpRequest);

        verify(userRepository, times(1)).save(captor.capture());
        assertNotNull(captor.getValue());
        assertEquals(email, captor.getValue().getEmail());
        assertEquals(password, captor.getValue().getPassword());
    }

    @Test
    public void whenUserAlreadyExist_ShouldWrapDataIntegrityException(){
        String email = "email";
        String password = "password";
        signUpRequest.setEmail(email);
        signUpRequest.setPassword(password);
        when(userRepository.save(any(User.class))).thenThrow(dataIntegrityViolationException);

        Exception exception = assertThrows(UserAlreadyExistsException.class, () -> userService.registerUser(signUpRequest));
        assertEquals("a user with a given email already exists", exception.getMessage());

    }
}
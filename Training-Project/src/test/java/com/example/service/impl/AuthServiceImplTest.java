package com.example.service.impl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequestDto;
import com.example.dto.RegisterUserDto;
import com.example.entities.User;
import com.example.enums.Role;
import com.example.enums.UserStatus;
import com.example.exception.ConflictException;
import com.example.exception.ForbiddenException;
import com.example.exception.InvalidCredentialsException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;
import com.example.security.JwtUtil;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {
	@Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;
    
    @InjectMocks
    private AuthServiceImpl authService;

    private RegisterUserDto registerDto;
    private LoginRequestDto loginDto;
    private User user;
    
    
    @BeforeEach
    void setUp() {
        registerDto = new RegisterUserDto();
        registerDto.setFirstName("Vivek");
        registerDto.setLastName("Giri");
        registerDto.setEmail("vivek@gmail.com");
        registerDto.setPassword("Password@123");
        registerDto.setAadhar("998877665544");
        registerDto.setContactNo("9123456789");

        loginDto = new LoginRequestDto();
        loginDto.setEmail("vivek@gmail.com");
        loginDto.setPassword("Password@123");

        user = new User();
        user.setId(1L);
        user.setEmail("vivek@gmail.com");
        user.setPassword("encoded-password");
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);
    }
    // ---------------- REGISTER ----------------

    @Test
    void registerUserTest() {

        when(userRepository.findByEmail("vivek@gmail.com"))
                .thenReturn(Optional.empty());
        when(passwordEncoder.encode(registerDto.getPassword()))
                .thenReturn("encoded-password");
        when(jwtUtil.generateToken(anyString(), anyString()))
                .thenReturn("jwt-token");

        AuthResponse response = authService.registerUser(registerDto);

        assertNotNull(response);
        assertEquals("User Registered Successfully", response.getMessage());
        assertEquals("jwt-token", response.getToken());

        verify(userRepository).save(any(User.class));
    }

    @Test
    void registerUser_emailAlreadyExistsTest() {

        when(userRepository.findByEmail("vivek@gmail.com"))
                .thenReturn(Optional.of(user));

        assertThrows(
                ConflictException.class,
                () -> authService.registerUser(registerDto)
        );

        verify(userRepository, never()).save(any());
    }

    // ---------------- LOGIN ----------------

    @Test
    void loginUserTest() {

        when(userRepository.findByEmail("vivek@gmail.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches("Password@123", "encoded-password"))
                .thenReturn(true);
        when(jwtUtil.generateToken(anyString(), anyString()))
                .thenReturn("jwt-token");

        AuthResponse response = authService.loginUser(loginDto);

        assertNotNull(response);
        assertEquals("Login Successful", response.getMessage());
        assertEquals("jwt-token", response.getToken());
    }

    @Test
    void loginUser_userNotFoundTest() {

        when(userRepository.findByEmail("vivek@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> authService.loginUser(loginDto)
        );
    }

    @Test
    void loginUser_userInactiveTest() {

        user.setStatus(UserStatus.INACTIVE);

        when(userRepository.findByEmail("vivek@gmail.com"))
                .thenReturn(Optional.of(user));

        assertThrows(
                ForbiddenException.class,
                () -> authService.loginUser(loginDto)
        );
    }

    @Test
    void loginUser_invalidPasswordTest() {

        when(userRepository.findByEmail("vivek@gmail.com"))
                .thenReturn(Optional.of(user));
        when(passwordEncoder.matches(anyString(), anyString()))
                .thenReturn(false);

        assertThrows(
                InvalidCredentialsException.class,
                () -> authService.loginUser(loginDto)
        );
    }
}

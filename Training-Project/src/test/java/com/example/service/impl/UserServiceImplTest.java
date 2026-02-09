package com.example.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.dto.UserResponseDto;
import com.example.entities.User;
import com.example.enums.Role;
import com.example.enums.UserStatus;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.UnauthorizedException;
import com.example.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private SecurityContext securityContext;

    @Mock
    private Authentication authentication;

    @InjectMocks
    private UserServiceImpl userService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Vivek");
        user.setLastName("Giri");
        user.setEmail("vivek@gmail.com");
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedDate(LocalDateTime.now());

        SecurityContextHolder.setContext(securityContext);
    }

    @AfterEach
    void tearDown() {
        SecurityContextHolder.clearContext();
    }

    // ================= AUTHENTICATION FAIL =================

    @Test
    void getCurrentUserProfileUnAuthenticatedTest() {

        when(securityContext.getAuthentication())
                .thenReturn(null);
        

        assertThrows(
                UnauthorizedException.class,
                () -> userService.getCurrentUserProfile()
        );
    }

    // ================= USER NOT FOUND =================

    @Test
    void getCurrentUserProfile_userNotFoundTest() {

        when(securityContext.getAuthentication())
                .thenReturn(authentication);
        when(authentication.isAuthenticated())
                .thenReturn(true);
        when(authentication.getName())
                .thenReturn("vivek@gmail.com");

        when(userRepository.findByEmail("vivek@gmail.com"))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> userService.getCurrentUserProfile()
        );
    }

    // ================= SUCCESS =================

    @Test
    void getCurrentUserProfileTset() {

        when(securityContext.getAuthentication())
                .thenReturn(authentication);
        when(authentication.isAuthenticated())
                .thenReturn(true);
        when(authentication.getName())
                .thenReturn("vivek@gmail.com");

        when(userRepository.findByEmail("vivek@gmail.com"))
                .thenReturn(Optional.of(user));

        UserResponseDto response =
                userService.getCurrentUserProfile();

        assertNotNull(response);
        assertEquals("Vivek", response.getFirstName());
        assertEquals("vivek@gmail.com", response.getEmail());
    }

    // ================= DELETE USER =================

    @Test
    void deleteCurrentUserTest() {

        when(securityContext.getAuthentication())
                .thenReturn(authentication);
        when(authentication.isAuthenticated())
                .thenReturn(true);
        when(authentication.getName())
                .thenReturn("vivek@gmail.com");

        when(userRepository.findByEmail("vivek@gmail.com"))
                .thenReturn(Optional.of(user));

        userService.deleteCurrentUser();

        verify(userRepository).delete(user);
    }
}

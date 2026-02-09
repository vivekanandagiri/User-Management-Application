package com.example.service.impl;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.dto.UserResponseDto;
import com.example.entities.User;
import com.example.enums.Role;
import com.example.enums.UserStatus;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;

@ExtendWith(MockitoExtension.class)
class AdminServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private AdminServiceImpl adminService;

    private User user;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setFirstName("Vivek");
        user.setLastName("Giri");
        user.setEmail("vivek@gmail.com");
        user.setAadhar("998877665544");
        user.setContactNo("9123456789");
        user.setRole(Role.USER);
        user.setStatus(UserStatus.ACTIVE);
        user.setCreatedDate(LocalDateTime.now());
    }

    // ================= UPDATE STATUS =================

    @Test
    void updateUserStatus_nullStatusTest() {

        assertThrows(
                BadRequestException.class,
                () -> adminService.updateUserStatus(1L, null)
        );
    }

    @Test
    void updateUserStatus_userNotFoundTest() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> adminService.updateUserStatus(1L, UserStatus.ACTIVE)
        );
    }

    @Test
    void updateUserStatusTest() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        adminService.updateUserStatus(1L, UserStatus.INACTIVE);

        assertEquals(UserStatus.INACTIVE, user.getStatus());
        verify(userRepository).save(user);
    }

    // ================= GET ALL USERS =================

    @Test
    void getAllUsersTest() {

        when(userRepository.findAll())
                .thenReturn(List.of(user));

        List<UserResponseDto> users = adminService.getAllUsers();

        assertEquals(1, users.size());
        assertEquals("Vivek", users.get(0).getFirstName());
    }

    // ================= UPDATE USER =================

    @Test
    void updateUser_userNotFoundTest() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> adminService.updateUser(new UserResponseDto(), 1L)
        );
    }

    @Test
    void updateUserTest() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        when(userRepository.save(any(User.class)))
                .thenReturn(user);

        UserResponseDto dto = new UserResponseDto();
        dto.setFirstName("Updated");
        dto.setLastName("User");

        UserResponseDto response =
                adminService.updateUser(dto, 1L);

        assertEquals("Updated", response.getFirstName());
    }

    // ================= FETCH USER =================

    @Test
    void fetchUserById_userNotFoundTest() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> adminService.fetchUserById(1L)
        );
    }

    @Test
    void fetchUserByIdTest() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        UserResponseDto dto = adminService.fetchUserById(1L);

        assertEquals("Vivek", dto.getFirstName());
        assertEquals("vivek@gmail.com", dto.getEmail());
    }

    // ================= DELETE USER =================

    @Test
    void deleteUserById_userNotFoundTest() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.empty());

        assertThrows(
                ResourceNotFoundException.class,
                () -> adminService.deleteUserById(1L)
        );
    }

    @Test
    void deleteUserByIdTest() {

        when(userRepository.findById(1L))
                .thenReturn(Optional.of(user));

        adminService.deleteUserById(1L);

        verify(userRepository).delete(user);
    }
}

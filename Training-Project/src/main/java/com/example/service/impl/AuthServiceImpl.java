package com.example.service.impl;

import java.time.LocalDateTime;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
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
import com.example.service.AuthService;

@Service
public class AuthServiceImpl implements AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtil jwtUtil;

    public AuthServiceImpl(
            UserRepository userRepository,
            PasswordEncoder passwordEncoder,
            JwtUtil jwtUtil) {

        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtil = jwtUtil;
    }

    // Register
    @Override
    public AuthResponse registerUser(RegisterUserDto dto) {

        String email = dto.getEmail().toLowerCase();

        if (userRepository.findByEmail(email).isPresent()) {
        	throw new ConflictException(
                    "User already exists with this email"
                );
        }

        User user = new User();

        user.setFirstName(dto.getFirstName());
        user.setLastName(dto.getLastName());
        user.setAadhar(dto.getAadhar());
        user.setEmail(email);

        user.setPassword(
            passwordEncoder.encode(dto.getPassword())
        );

        user.setContactNo(dto.getContactNo());

        user.setRole(Role.USER);
        user.setStatus(UserStatus.INACTIVE);
        user.setCreatedDate(LocalDateTime.now());

        userRepository.save(user);

        String token = jwtUtil.generateToken(
            user.getEmail(),
            user.getRole().name()
        );

        return AuthResponse.builder()
                .message("User Registered Successfully")
                .token(token)
                .build();
    }

    // Login
    @Override
    public AuthResponse loginUser(LoginRequestDto dto) {

        String email = dto.getEmail().toLowerCase();

        User user = userRepository.findByEmail(email)
            .orElseThrow(() ->
                new ResourceNotFoundException("User not found with this email:"+email)
            );

        // Status check
        if (user.getStatus() != UserStatus.ACTIVE) {
        	throw new ForbiddenException(
                    "Your account is not active. Contact admin"
                );
        }

        // Password check
        if (!passwordEncoder.matches(
                dto.getPassword(),
                user.getPassword()
        )) {
        	throw new InvalidCredentialsException(
                    "Invalid email or password"
                );
        }

        String token = jwtUtil.generateToken(
            user.getEmail(),
            user.getRole().name()
        );

        return AuthResponse.builder()
                .message("Login Successful")
                .token(token)
                .build();
    }
}

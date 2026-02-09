package com.example.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.AuthApi;
import com.example.dto.AuthResponse;
import com.example.dto.LoginRequestDto;
import com.example.dto.RegisterUserDto;
import com.example.service.AuthService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class AuthController implements AuthApi {
	private final AuthService authService;

	public AuthController(AuthService authService) {
		this.authService = authService;
	}
	
	@Override
	public ResponseEntity<AuthResponse> registerUser(
	        @Valid @RequestBody RegisterUserDto registerUserDto) {

	    AuthResponse response = authService.registerUser(registerUserDto);
	    return ResponseEntity.status(HttpStatus.CREATED).body(response);
	}

	
	@Override
	public ResponseEntity<AuthResponse> loginUser(
	        @Valid @RequestBody LoginRequestDto loginRequestDto) {

	    return ResponseEntity.ok(authService.loginUser(loginRequestDto));
	}

}

package com.example.service;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequestDto;
import com.example.dto.RegisterUserDto;

public interface AuthService {

	public AuthResponse registerUser(RegisterUserDto registerUserDto);
	
	public AuthResponse loginUser(LoginRequestDto loginRequestDto);
	
}

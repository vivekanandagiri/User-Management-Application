package com.example.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import com.example.dto.AuthResponse;
import com.example.dto.LoginRequestDto;
import com.example.dto.RegisterUserDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@Tag(name = "Authentication Management", description = "Authentication related APIs")
public interface AuthApi {
	@Operation(summary = "Register a new User")
	@ApiResponses({
		@ApiResponse(
				responseCode = "201",
				description = "User Registered",
				content = @Content(
						schema = @Schema(implementation = AuthResponse.class)
						)
				),
		@ApiResponse(responseCode = "400",description = "Invalid input"),
		@ApiResponse(responseCode = "409",description = "User already exist")
	})
	@PostMapping("/register")
	ResponseEntity<AuthResponse> registerUser(@Valid @RequestBody RegisterUserDto registerUserDto);
	
	@Operation(summary = "User Login")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "User Login Successfully",
				content = @Content(
						schema = @Schema(implementation = AuthResponse.class)
						)
				),
		@ApiResponse(responseCode = "401",description = "Invalid credentials")
	})
	@PostMapping("/login")
	ResponseEntity<AuthResponse> loginUser(@Valid @RequestBody LoginRequestDto loginRequestDto);

}
//Here multiple @Valid and @RequestBody in the both interface and implementation class So i have to remove it from one place 
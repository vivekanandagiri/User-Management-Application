package com.example.controllers;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.UserApi;
import com.example.dto.UserResponseDto;
import com.example.service.UserService;

@RestController
@RequestMapping("/api/user")
public class UserController implements UserApi {
	
	//Constructor Injection
	private final UserService userService;
	
	public UserController(UserService userService) {
		super();
		this.userService = userService;
	}

	//Get current active User
	@Override
	public ResponseEntity<UserResponseDto> currentUser() {
		UserResponseDto user = userService.getCurrentUserProfile();
		return ResponseEntity.ok(user);
		
	}
	//Delete current Active User 

	@Override
	public ResponseEntity<Void> deleteUser() {
		userService.deleteCurrentUser();
		return ResponseEntity.noContent().build();
	}
	
	
}

package com.example.service;

import com.example.dto.UserResponseDto;

public interface UserService {
	
	UserResponseDto getCurrentUserProfile();
	
	void deleteCurrentUser();
	
	

}

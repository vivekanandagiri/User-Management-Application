package com.example.service;

import java.util.List;

import com.example.dto.UserResponseDto;
import com.example.enums.UserStatus;

public interface AdminService {
	
	void updateUserStatus(Long userId, UserStatus status);
	
	List<UserResponseDto>getAllUsers();
	
	public UserResponseDto updateUser(UserResponseDto userResponseDto,Long id);
	
	public UserResponseDto fetchUserById(Long id);

	void deleteUserById(Long id);

}

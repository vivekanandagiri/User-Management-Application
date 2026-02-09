package com.example.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.api.AdminApi;
import com.example.dto.ApiResponse;
import com.example.dto.UpdateUserStatusDto;
import com.example.dto.UserResponseDto;
import com.example.service.AdminService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/admin/users")
public class AdminController implements AdminApi {
	
	//Constructor Injection
	private final AdminService adminService;
	public AdminController(AdminService adminService) {
		this.adminService = adminService;
	}
	
	@Override
	public ResponseEntity<List<UserResponseDto>>getAllUsers(){
		List<UserResponseDto> users =adminService.getAllUsers();
		
		return ResponseEntity.ok(users);
	}
	
	
	@Override
	public ResponseEntity<UserResponseDto> fetchUser(Long id) {
		UserResponseDto dto = adminService.fetchUserById(id);
		return ResponseEntity.ok(dto);
	}
	
	@Override
	public ResponseEntity<ApiResponse<Void>> updateStatus(
	        @PathVariable Long id,
	        @Valid @RequestBody UpdateUserStatusDto dto) {

	    adminService.updateUserStatus(id, dto.getStatus());

	    return ResponseEntity.ok(
	        new ApiResponse<>(
	            "User status updated successfully",
	            null
	        )
	    );
	}
	
	@Override
	public ResponseEntity<ApiResponse<UserResponseDto>> updateUser(
	        @PathVariable Long id,
	        @Valid @RequestBody UserResponseDto dto) {

	    UserResponseDto updatedUser =
	        adminService.updateUser(dto, id);

	    ApiResponse<UserResponseDto> response =
	        new ApiResponse<>(
	            "User updated successfully",
	            updatedUser
	        );
	    return ResponseEntity.ok(response);
	}

	@Override
	public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

	    adminService.deleteUserById(id);

	    return ResponseEntity.noContent().build(); 
	}

	

}

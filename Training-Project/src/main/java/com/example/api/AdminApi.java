package com.example.api;

import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.dto.UpdateUserStatusDto;
import com.example.dto.UserResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
@Tag(name = "Admin Management", description = "Admin related APIs")
public interface AdminApi {
	//--------------------------Get All Users -------------------
	@Operation(summary = "Get All Users(Admin only)")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "Users Fetched Successfully"
				),
		@ApiResponse(responseCode = "403",description = "Access Denied"),
	})
	@GetMapping
    @PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<List<UserResponseDto>> getAllUsers();
	
	//-------------------------------Fetch User By id--------------
	@Operation(summary = "Get Perticular User By Id(Admin only)")
	@ApiResponses({
		@ApiResponse(
				responseCode = "200",
				description = "User Fetched Successfully"
				),
		@ApiResponse(responseCode = "403",description = "Access Denied"),
	})
	@GetMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<UserResponseDto>fetchUser(@PathVariable("id") Long id);
	
	
	//-------------------------------Update Status of User----------------------------------
	@Operation(summary = "Update user status (Admin only)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User status updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
	@PatchMapping("/{id}/status")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<com.example.dto.ApiResponse<Void>> updateStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateUserStatusDto dto
    );
	
	//--------------------------Update User Details ----------------
	
	@Operation(summary = "Update user details (Admin only)")
    @ApiResponses({
        @ApiResponse(responseCode = "200", description = "User updated successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid input"),
        @ApiResponse(responseCode = "403", description = "Access denied"),
        @ApiResponse(responseCode = "404", description = "User not found")
    })
	@PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    ResponseEntity<com.example.dto.ApiResponse<UserResponseDto>> updateUser(
            @PathVariable Long id,
            @Valid @RequestBody UserResponseDto dto
    );
	//--------------------------Delete User  ----------------
	@Operation(summary = "Delete a authenticated user")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "User deleted successfully"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized"
        )
    })
	@DeleteMapping("/{id}")
	@PreAuthorize("hasRole('ADMIN')")
	ResponseEntity<Void> deleteUser(@PathVariable Long  id);
}

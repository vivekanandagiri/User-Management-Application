package com.example.api;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import com.example.dto.UserResponseDto;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
@Tag(name = "User Management", description = "User related APIs")
public interface UserApi {
	@Operation(summary = "Get current authenticated user")
    @ApiResponses({
        @ApiResponse(
            responseCode = "200",
            description = "User fetched successfully"
        ),
        @ApiResponse(
            responseCode = "401",
            description = "Unauthorized"
        )
    })
    @GetMapping
    ResponseEntity<UserResponseDto> currentUser();
	
	@Operation(summary = "Delete current authenticated user")
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
	@DeleteMapping
	ResponseEntity<Void> deleteUser();

}

package com.example.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


import java.time.LocalDateTime;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.dto.UpdateUserStatusDto;
import com.example.dto.UserResponseDto;
import com.example.enums.Role;
import com.example.enums.UserStatus;
import com.example.security.JwtFilter;
import com.example.security.JwtUtil;
import com.example.service.AdminService;
import com.example.service.UserService;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AdminControllerTest {
	
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private AdminService adminService;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private JwtUtil jwtUtil;

	@Test
	void getAllUsersTest() throws Exception {
		//Dummy data
		UserResponseDto user1 = new UserResponseDto(
                1L, 
                "Vivek", 
                "Giri", 
                "Vivek@123",
                "vivek@gmail.com", 
                "9999999999",
                Role.USER, 
                UserStatus.ACTIVE, 
                LocalDateTime.now()
        );
		
		UserResponseDto user2 = new UserResponseDto(
                2L,
                "Admin", 
                "User", 
                "Admin@456",
                "admin@gmail.com", 
                "8888888888",
                Role.ADMIN, UserStatus.ACTIVE, LocalDateTime.now()
        );
		
		when(adminService.getAllUsers()).thenReturn(List.of(user1, user2));
		
		mockMvc.perform(get("/api/admin/users"))
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.length()").value(2))
        .andExpect(jsonPath("$[0].email").value("vivek@gmail.com"))
        .andExpect(jsonPath("$[1].role").value("ADMIN"));
		
	}
	// ---------------------------------------------------------
    // FETCH USER BY ID
    // ---------------------------------------------------------
    @Test
    void fetchUserByIdTest() throws Exception {

        UserResponseDto user = new UserResponseDto(
                1L,
                "Vivek",
                "Giri",
                "123",
                "vivek@gmail.com",
                "9999999999",
                Role.USER,
                UserStatus.ACTIVE,
                LocalDateTime.now()
        );

        when(adminService.fetchUserById(1L)).thenReturn(user);

        mockMvc.perform(get("/api/admin/users/1"))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.id").value(1))
               .andExpect(jsonPath("$.email").value("vivek@gmail.com"));
    }

	 // ---------------------------------------------------------
    // UPDATE USER STATUS
    // ---------------------------------------------------------
    @Test
    void updateUserStatusTest() throws Exception {

        UpdateUserStatusDto dto = new UpdateUserStatusDto();
        dto.setStatus(UserStatus.INACTIVE);

        doNothing().when(adminService)
                   .updateUserStatus(eq(1L), eq(UserStatus.INACTIVE));

        mockMvc.perform(patch("/api/admin/users/1/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "status": "INACTIVE"
                            }
                        """))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message")
               .value("User status updated successfully"));
    }
    
 // ---------------------------------------------------------
    // UPDATE USER DETAILS
    // ---------------------------------------------------------
    @Test
    void updateUserTest() throws Exception {

        UserResponseDto updatedUser = new UserResponseDto(
                1L, "Updated", "User", "123",
                "updated@gmail.com", "7777777777",
                Role.USER, UserStatus.ACTIVE, LocalDateTime.now()
        );

        when(adminService.updateUser(any(UserResponseDto.class), eq(1L)))
                .thenReturn(updatedUser);

        mockMvc.perform(put("/api/admin/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                            {
                              "firstName": "Updated",
                              "lastName": "User",
                              "email": "updated@gmail.com"
                            }
                        """))
               .andExpect(status().isOk())
               .andExpect(jsonPath("$.message")
               .value("User updated successfully"))
               .andExpect(jsonPath("$.data.email")
               .value("updated@gmail.com"));
    }

    // ---------------------------------------------------------
    // DELETE USER
    // ---------------------------------------------------------
    @Test
    void deleteUserTest() throws Exception {

        doNothing().when(adminService).deleteUserById(1L);

        mockMvc.perform(delete("/api/admin/users/1"))
               .andExpect(status().isNoContent());
    }
}

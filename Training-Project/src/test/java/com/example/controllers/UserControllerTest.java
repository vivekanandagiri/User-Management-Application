package com.example.controllers;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.time.LocalDateTime;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import com.example.dto.UserResponseDto;
import com.example.enums.Role;
import com.example.enums.UserStatus;
import com.example.security.JwtFilter;
import com.example.security.JwtUtil;
import com.example.service.UserService;

@WebMvcTest(controllers = UserController.class,
    excludeAutoConfiguration = {
        SecurityAutoConfiguration.class,
        SecurityFilterAutoConfiguration.class
    })
@AutoConfigureMockMvc(addFilters = false)
public class UserControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private JwtUtil jwtUtil;


	
	@Test
	void currentLoggedInUserViewTest() throws Exception {

	    UserResponseDto dto = new UserResponseDto();
	    dto.setId(1L);
	    dto.setFirstName("Vivekananda");
	    dto.setLastName("Giri");
	    dto.setAadhar("123456789012");
	    dto.setEmail("vivek@gmail.com");
	    dto.setContactNo("9876543210");
	    dto.setRole(Role.USER);
	    dto.setStatus(UserStatus.ACTIVE);
	    dto.setCreatedDate(LocalDateTime.now());

	    when(userService.getCurrentUserProfile()).thenReturn(dto);

	    mockMvc.perform(get("/api/user"))
	           .andExpect(status().isOk())
	           .andExpect(jsonPath("$.id").value(1))
	           .andExpect(jsonPath("$.firstName").value("Vivekananda"))
	           .andExpect(jsonPath("$.lastName").value("Giri"))
	           .andExpect(jsonPath("$.aadhar").value("123456789012"))
	           .andExpect(jsonPath("$.email").value("vivek@gmail.com"))
	           .andExpect(jsonPath("$.contactNo").value("9876543210"))
	           .andExpect(jsonPath("$.role").value("USER"))
	           .andExpect(jsonPath("$.status").value("ACTIVE"));
	}
	@Test
	void deleteCurrentUserTest() throws Exception {

	    mockMvc.perform(delete("/api/user"))
	           .andExpect(status().isNoContent());
	}


	
}

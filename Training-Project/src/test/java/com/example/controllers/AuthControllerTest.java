package com.example.controllers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.dto.AuthResponse;
import com.example.dto.LoginRequestDto;
import com.example.dto.RegisterUserDto;
import com.example.security.JwtFilter;
import com.example.security.JwtUtil;
import com.example.service.AuthService;
import com.example.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@WebMvcTest(AuthController.class)
@AutoConfigureMockMvc(addFilters = false)
public class AuthControllerTest {
	@Autowired
	private MockMvc mockMvc;
	
	@MockBean
	private UserService userService;
	
	@MockBean
	private JwtFilter jwtFilter;

	@MockBean
	private JwtUtil jwtUtil;
	@MockBean
    private AuthService authService;

    @Autowired
    private ObjectMapper objectMapper;
    
    // ========================= REGISTER =========================

    @Test
    void registerUserTest() throws Exception {

        RegisterUserDto request = new RegisterUserDto();
        request.setFirstName("Vivek");
        request.setLastName("Giri");
        request.setAadhar("998877665544");
        request.setEmail("vivek@gmail.com");
        request.setPassword("Vivek@123");
        request.setContactNo("9123456789");

        AuthResponse response = AuthResponse.builder()
                .message("User Registered Successfully")
                .token("dummy-jwt-token")
                .build();

        when(authService.registerUser(any(RegisterUserDto.class)))
                .thenReturn(response);

        mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isCreated())
        .andExpect(jsonPath("$.message").value("User Registered Successfully"))
        .andExpect(jsonPath("$.token").value("dummy-jwt-token"));
    }

    // ========================= LOGIN =========================

    @Test
    void loginUserTest() throws Exception {

        LoginRequestDto request = new LoginRequestDto();
        request.setEmail("vivek@gmail.com");
        request.setPassword("password123");

        AuthResponse response =
                new AuthResponse("Login successful", "jwt-token");

        when(authService.loginUser(request)).thenReturn(response);

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isOk())
        .andExpect(jsonPath("$.message").value("Login successful"))
        .andExpect(jsonPath("$.token").value("jwt-token"));
    }

    // ========================= 400 VALIDATION TESTS =========================

    @Test
    void registerUser_missingFields() throws Exception {

        RegisterUserDto request = new RegisterUserDto(); // empty DTO

        mockMvc.perform(
                post("/api/auth/register")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request))
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void loginUser_invalidEmail() throws Exception {

        String invalidJson = """
            {
              "email": "invalid-email",
              "password": ""
            }
            """;

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(invalidJson)
        )
        .andExpect(status().isBadRequest());
    }

    @Test
    void loginUser_missingBody() throws Exception {

        mockMvc.perform(
                post("/api/auth/login")
                        .contentType(MediaType.APPLICATION_JSON)
        )
        .andExpect(status().isBadRequest());
    }

}

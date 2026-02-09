package com.example.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class RegisterUserDto {
	
	@NotBlank(message = "First name is required")
    @Size(min = 2, max = 30, message = "First name must be between 2 and 30 characters")
    @Pattern(regexp = "^[\\p{L}\\s.'-]+$", message = "First name contains invalid characters")
    private String firstName;
	
	@NotBlank(message = "Last name is required")
    @Size(min = 2, max = 30, message = "Last name must be between 2 and 30 characters")
    @Pattern(regexp = "^[\\p{L}\\s.'-]+$", message = "Last name contains invalid characters")
    private String lastName;
	
	@NotBlank(message = "Aadhar is required")
    @Pattern(regexp = "\\d{12}", message = "Aadhar number must be exactly 12 digits")
    private String aadhar;
	
	@Email
    private String email;
	
	@Size(min = 6,message = "Password Should contains min 6 characters")
    private String password;
	
	@Pattern(regexp = "^[0-9]{10}$",message = "Contact No must contains 10 digits ")
    private String contactNo;
}

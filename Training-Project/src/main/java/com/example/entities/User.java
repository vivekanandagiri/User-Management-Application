package com.example.entities;


import java.time.LocalDateTime;

import org.springframework.data.annotation.CreatedDate;

import com.example.enums.Role;
import com.example.enums.UserStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "users")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@NotBlank(message = "First Name is required") 
	@Column(name = "first_name", nullable = false) 
	private String firstName;
	
	@NotBlank(message = "Last Name is required") 
	@Column(name = "last_name", nullable = false) 
	private String lastName;
	
	@NotBlank(message = "ID order is required")
    @Pattern(regexp = "\\d{12}", message = "Aadhar number must be exactly 12 digits") 
	@Column(unique = true, nullable = false) 
	private String aadhar;
	
	@Email(message = "Email address must be valid") 
	@NotBlank(message = "Email is required") 
	@Column(unique = true, nullable = false) 
	private String email;
	
	
	@NotBlank(message = "Password is required") 
	@Size(min = 6, message = "Password must be at least 6 characters long") 
	@Column(nullable = false) 
	private String password;
	
	@NotBlank(message = "Contact number is required")
	@Pattern(regexp = "^[0-9]{10}$", message = "Contact number must be exactly 10 digits")
	@Column(name = "contact_no", nullable = false, unique = true)
	private String contactNo;


	@Enumerated(EnumType.STRING)
	@Column(name = "role", nullable = false) 
	private Role role;
	
	@Enumerated(EnumType.STRING) 
	@Column(name = "user_status", nullable = false) 
	private UserStatus status;
	
	@CreatedDate 
	@Column(name = "created_date", nullable = false, updatable = false) 
	private LocalDateTime createdDate;
	

	

}

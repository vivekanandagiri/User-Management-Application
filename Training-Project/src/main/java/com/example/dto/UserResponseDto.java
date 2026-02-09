package com.example.dto;

import java.time.LocalDateTime;
import com.example.enums.Role;
import com.example.enums.UserStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserResponseDto {
    private Long id;
    private String firstName;
    private String lastName;
    private String aadhar;
    private String email;
    private String contactNo;
    private Role role;
    private UserStatus status;
    private LocalDateTime createdDate;
}

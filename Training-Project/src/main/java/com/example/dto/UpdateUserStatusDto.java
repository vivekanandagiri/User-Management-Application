package com.example.dto;

import com.example.enums.UserStatus;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateUserStatusDto {

	@NotNull(message = "Status is required")
    private UserStatus status;
}

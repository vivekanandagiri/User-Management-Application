package com.example.service.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.dto.UserResponseDto;
import com.example.entities.User;
import com.example.enums.UserStatus;
import com.example.exception.BadRequestException;
import com.example.exception.ResourceNotFoundException;
import com.example.repository.UserRepository;
import com.example.service.AdminService;
@Service
public class AdminServiceImpl implements AdminService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;

	public AdminServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	//Mapping User to DTO
		private UserResponseDto mapToDTO(User user) {

		    return new UserResponseDto(
		        user.getId(),
		        user.getFirstName(),
		        user.getLastName(),
		        user.getAadhar(),       
		        user.getEmail(),         
		        user.getContactNo(),
		        user.getRole(),
		        user.getStatus(),
		        user.getCreatedDate()
		    );
		}
		//User Activation and Inactivation
		@Override
		public void updateUserStatus(Long userId, UserStatus status) {

		    if (status == null) {
		        throw new BadRequestException("Status cannot be null");
		    }

		    User user = userRepository.findById(userId)
		        .orElseThrow(() ->
		            new ResourceNotFoundException(
		                "User not found with id: " + userId
		            )
		        );

		    user.setStatus(status);
		    userRepository.save(user);
		}
		//Get All User for Admin
		@Override
		public List<UserResponseDto> getAllUsers() {

	        return userRepository.findAll()
	                .stream()
	                .map(this::mapToDTO)
	                .toList();
	    }
		
		//Update User Details for Admin
		@Override
		public UserResponseDto updateUser(UserResponseDto userResponseDto, Long id) {
			User existingUser = userRepository.findById(id)
					.orElseThrow(()->
					new ResourceNotFoundException(
		                    "User not found with id: " + id
		                )
		            );
			
			BeanUtils.copyProperties(userResponseDto, existingUser,"id");
			User updatedUser = userRepository.save(existingUser);
			UserResponseDto updatedDto = new UserResponseDto();
			BeanUtils.copyProperties(updatedUser, updatedDto);
			
			return updatedDto;
		}
		@Override
		public UserResponseDto fetchUserById(Long id) {
			User user = userRepository.findById(id)
			.orElseThrow(
			()->new ResourceNotFoundException("User with this is "+id+" not found  "));
			UserResponseDto dto = new UserResponseDto();
			BeanUtils.copyProperties(user, dto);
			return dto ;
		}
		
		@Override
		public void deleteUserById(Long id) {
		    User userToDelete = userRepository.findById(id)
		        .orElseThrow(() ->
		            new ResourceNotFoundException(
		                "User not found with id: " + id));

		    userRepository.delete(userToDelete);
		}
		


}

package com.example.service.impl;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.example.dto.UserResponseDto;
import com.example.entities.User;
import com.example.exception.ResourceNotFoundException;
import com.example.exception.UnauthorizedException;
import com.example.repository.UserRepository;
import com.example.service.UserService;
@Service
public class UserServiceImpl implements UserService {
	
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	//Dependency Injection Using constructor
	public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}

	//Get Logged in Profile 
	private User getLoggedinUser() {
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		
		if (auth == null || !auth.isAuthenticated()) {
			throw new UnauthorizedException(
	                "User is not authenticated"
	            );
	    }
		String email=auth.getName();
		//for debugging 
		//System.out.println("AUTH OBJECT = " + auth);
		//System.out.println("AUTH NAME = " + auth.getName());
		return userRepository.findByEmail(email)
				.orElseThrow(()->
				new ResourceNotFoundException(
	                    "User not found with email: " + email
	                )
	            );
						
	}
	
	//Get Current Logged in User
	@Override
	public UserResponseDto getCurrentUserProfile() {
		
		User user = getLoggedinUser();
		
		
		return mapToDTO(user);
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

	@Override
	public void deleteCurrentUser() {
		User loggedinUser = getLoggedinUser();
		 userRepository.delete(loggedinUser);
		
	}
	
	

	

}

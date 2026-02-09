package com.example.config;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.example.entities.User;
import com.example.enums.Role;
import com.example.enums.UserStatus;
import com.example.repository.UserRepository;

@Configuration
public class DataInitializer {
	@Bean
	CommandLineRunner createDefaultAdmin(UserRepository userRepository,PasswordEncoder passwordEncoder) {
		return args->{
			String adminEmail="admin@gmail.com";
			
			//check if Admin exists
			if(userRepository.findByEmail(adminEmail).isEmpty()) {
				User admin = new User();
				
				admin.setFirstName("System");
				admin.setLastName("Admin");
				admin.setEmail(adminEmail);
				admin.setPassword(passwordEncoder.encode("Admin@787"));
				admin.setContactNo("9078285665");
				admin.setAadhar("123456789012");
				
				admin.setRole(Role.ADMIN);
				admin.setStatus(UserStatus.ACTIVE);
				
				admin.setCreatedDate(LocalDateTime.now());
				
				userRepository.save(admin);
				
				System.out.println("Default ADMIN created !!!!");
			}
			else {
				System.out.println("Default Admin is already Exist");
				
			}
			
		};
	}

}

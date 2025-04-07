package com.web.services;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;

import com.web.dto.LoginDto;
import com.web.dto.UserDto;
import com.web.model.User;

public interface UserService {
	  User registerUser(UserDto userDTO);
	   // ResponseEntity<Map<String, String>> loginUser(LoginDto loginDto);
	  ResponseEntity<Map<String, Object>> loginUser(LoginDto loginDto); // Change return type

	  List<User> getAllUsers();
	    List<User> getAllJobSeekers(); // New method
	    List<User>getAllEmployers();
		void deleteUserById(Long id);
	    User updateUserDetails(Long userId, User updatedUser);


}





package com.web.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.web.dto.LoginDto;
import com.web.dto.UserDto;
import com.web.model.User;
import com.web.repository.UserRepository;

import jakarta.transaction.Transactional;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Transactional
    @Override
    public User registerUser(UserDto userDTO) {
        System.out.println("📌 Register API called with email: " + userDTO.getEmail());

        if (userRepository.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new RuntimeException("📌 Email already exists: " + userDTO.getEmail());
        }

        // No password hashing
        User user = new User();
        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword()); // Storing as plain text (not secure, but okay for demo)
        user.setRole(userDTO.getRole() != null ? userDTO.getRole() : "USER");
        user.setPhoneNumber(userDTO.getPhoneNumber());

        System.out.println("📌 User Object Before Saving: " + user);
        User savedUser = userRepository.save(user);
        System.out.println("📌 User Object After Saving: " + savedUser);

        return savedUser;
    }

    @Override

    public ResponseEntity<Map<String, Object>> loginUser(LoginDto loginDto) { 
        System.out.println("Login method called with email: " + loginDto.getEmail());

        Optional<User> optionalUser = userRepository.findByEmail(loginDto.getEmail());

        if (optionalUser.isEmpty()) {
            System.out.println("User not found!");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "User not found!"));
        }

        User user = optionalUser.get();

        if (!loginDto.getPassword().equals(user.getPassword())) {
            
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", "Invalid password!"));
        }

        // ✅ Print User ID before sending response
        System.out.println("User ID before sending response: " + user.getId());

        Map<String, Object> response = new HashMap<>();
        response.put("message", "Login successful");
        response.put("id", user.getId());  // ✅ Make sure ID is not null
        response.put("role", user.getRole());
        response.put("name", user.getName());
        response.put("email", user.getEmail());
        response.put("phoneNumber",user.getPhoneNumber());
        response.put("password", user.getPassword());;
        

        System.out.println("Login Response: " + response);  // ✅ Debugging step

        return ResponseEntity.ok().body(response);
    }


    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public List<User> getAllJobSeekers() {
        return userRepository.findAll().stream()
                .filter(user -> "USER".equals(user.getRole())) // Assuming "USER" is for job seekers
                .collect(Collectors.toList());
    }
    @Override
    public List<User> getAllEmployers() {
        return userRepository.findAll().stream()
                .filter(user -> "EMPLOYER".equalsIgnoreCase(user.getRole())) // Case-insensitive check
                .collect(Collectors.toList());
    }
    
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public User updateUserDetails(Long userId, User updatedUser) {
        User existingUser = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Update only the allowed fields
        existingUser.setName(updatedUser.getName());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPhoneNumber(updatedUser.getPhoneNumber());

        // Encode password before saving
        if (updatedUser.getPassword() != null && !updatedUser.getPassword().isEmpty()) {
            existingUser.setPassword(updatedUser.getPassword());
        }

        return userRepository.save(existingUser);
    }


}

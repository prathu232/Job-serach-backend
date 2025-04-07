package com.web.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.web.model.Admin;
import com.web.repository.AdminRepository;

@Service
public class AdminServicesImp implements AdminServices {


	@Autowired
	private AdminRepository adminRepository;
	
	public String login(String email, String password) {
	  //  System.out.println("Checking login for email: " + email);

	    Optional<Admin> adminOpt = adminRepository.findByEmail(email.trim());
	    if (adminOpt.isPresent()) {
	        Admin admin = adminOpt.get();
	     //   System.out.println("Admin found: " + admin.getEmail());

	        if (admin.getPassword().trim().equals(password.trim())) {
	        //    System.out.println("Password matched!");
	            return "Admin login successful";
	        } else {
	         //   System.out.println("Incorrect password");
	        }
	    } else {
	       // System.out.println("Admin not found for email: " + email);
	    }

	    throw new RuntimeException("Invalid username or password");
	}




}

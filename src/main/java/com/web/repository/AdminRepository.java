package com.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.web.model.Admin;

public interface AdminRepository extends JpaRepository<Admin, Long> {
	 Optional<Admin> findByEmail(String email);
}

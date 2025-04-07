package com.web.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.web.model.User;
@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	//public interface UserRepository extends JpaRepository<User, Long> {
	    Optional<User> findByEmail(String email);
	    Optional<User> findByName(String name);
	    boolean existsByEmail(String email);
	    boolean existsByPhoneNumber(String phoneNumber);

}

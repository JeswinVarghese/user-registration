package com.example.userregistration.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.userregistration.model.User;

public interface UserRepository extends JpaRepository<User, Long> {
	
	Optional<User> findByEmail(String email);

	boolean existsByEmail(String email);

	void deleteByEmail(String email);
}

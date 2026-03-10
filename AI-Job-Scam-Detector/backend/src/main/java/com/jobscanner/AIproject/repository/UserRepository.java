package com.jobscanner.AIproject.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jobscanner.AIproject.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

	
	User findByEmailIgnoreCase(String email);
}
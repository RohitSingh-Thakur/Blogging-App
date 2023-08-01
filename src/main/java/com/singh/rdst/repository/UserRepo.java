package com.singh.rdst.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.singh.rdst.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

	Optional<User> findByUserEmail(String userEmail);
}

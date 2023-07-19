package com.singh.rdst.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.singh.rdst.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}

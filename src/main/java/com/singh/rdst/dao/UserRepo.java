package com.singh.rdst.dao;

import org.springframework.data.jpa.repository.JpaRepository;

import com.singh.rdst.entity.User;

public interface UserRepo extends JpaRepository<User, Integer>{

}

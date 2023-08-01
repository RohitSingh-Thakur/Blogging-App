package com.singh.rdst.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.singh.rdst.entity.User;
import com.singh.rdst.exceptions.ResourceNotFoundException;
import com.singh.rdst.repository.UserRepo;

@Service
public class CustomUserDetailService implements UserDetailsService{

	@Autowired
	private UserRepo repo;
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		// Loading User from Database by username
		
		User user = this.repo.findByUserEmail(username).orElseThrow(() -> new ResourceNotFoundException("User", "Email : " + username, 0)); 
		// in above line we need to change the constructor of ResourceNotFoundException
		// but here this method return UserDetails and we have user object
		// so we need to implements the UserDetails class in User Table
		
		return user;
	}

}

package com.singh.rdst.services.impl;

import java.util.List; 
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.singh.rdst.entity.Role;
import com.singh.rdst.entity.User;
import com.singh.rdst.exceptions.ResourceNotFoundException;
import com.singh.rdst.payloads.UserDto;
import com.singh.rdst.repository.RoleRepo;
import com.singh.rdst.repository.UserRepo;
import com.singh.rdst.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.mapper.map(userDto, User.class);
		User savedUser = this.userRepo.save(user);
		return this.mapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserID",userId));
		
		user.setUserName(userDto.getUserName());
		user.setUserEmail(userDto.getUserEmail());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User upadtedUser = this.userRepo.save(user);
		
		return this.mapper.map(upadtedUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		Optional<User> user = Optional.ofNullable(this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)));
		return this.mapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		 List<UserDto> usersDto = users.stream().map(e -> this.mapper.map(e, UserDto.class)).collect(Collectors.toList());
		 return usersDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("USER", "ID", userId));
		this.userRepo.delete(user);
	}

	@Override
	public UserDto registerNewUser(UserDto userDto) {
		User user = this.mapper.map(userDto, User.class);
		// Before saving the user we need to encrypt the password using PasswordEncoder Interface which needs to be Autowired
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		// and we need to specify its role also
		// here for each new user we are assigning normal user to it that is role id 802 -> ROLE_USER
		
		Role userRole = this.roleRepo.findById(802).get();
		user.getRole().add(userRole);
		
		// Saving the user into Database
		User savedUser = this.userRepo.save(user);
		return this.mapper.map(savedUser, UserDto.class);
	}

}

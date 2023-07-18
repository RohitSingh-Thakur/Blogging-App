package com.singh.rdst.services.impl;

import java.util.List; 
import java.util.Optional;
import java.util.stream.Collectors;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.singh.rdst.dao.UserRepo;
import com.singh.rdst.entity.User;
import com.singh.rdst.exceptions.ResourceNotFoundException;
import com.singh.rdst.payloads.UserDto;
import com.singh.rdst.services.UserService;

@Service
public class UserServiceImpl implements UserService{
	
	@Autowired
	private UserRepo repo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public UserDto createUser(UserDto userDto) {
		User user = this.mapper.map(userDto, User.class);
		User savedUser = this.repo.save(user);
		return this.mapper.map(savedUser, UserDto.class);
	}

	@Override
	public UserDto updateUser(UserDto userDto, Integer userId) {
		User user = this.repo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User","UserID",userId));
		
		user.setUser_Name(userDto.getUser_Name());
		user.setUser_Email(userDto.getUser_Email());
		user.setPassword(userDto.getPassword());
		user.setAbout(userDto.getAbout());

		User upadtedUser = this.repo.save(user);
		
		return this.mapper.map(upadtedUser, UserDto.class);
	}

	@Override
	public UserDto getUserById(Integer userId) {
		Optional<User> user = Optional.ofNullable(this.repo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User", "Id", userId)));
		return this.mapper.map(user, UserDto.class);
	}

	@Override
	public List<UserDto> getAllUsers() {
		List<User> users = this.repo.findAll();
		 List<UserDto> usersDto = users.stream().map(e -> this.mapper.map(e, UserDto.class)).collect(Collectors.toList());
		 return usersDto;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.repo.findById(userId).orElseThrow(() -> new ResourceNotFoundException("USER", "ID", userId));
		this.repo.delete(user);
	}

}

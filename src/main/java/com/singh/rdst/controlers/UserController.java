package com.singh.rdst.controlers;

import java.util.List; 
import java.util.Map;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.singh.rdst.payloads.UserDto;
import com.singh.rdst.services.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {
	
	@Autowired
	private UserService service;

	@PostMapping("/createUser")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody UserDto userDto){
		UserDto user = this.service.createUser(userDto);
		return new ResponseEntity<UserDto>(user, HttpStatus.CREATED);
	}
	
	@PutMapping("/updateUser/{userId}")
	public ResponseEntity<UserDto> updateUser(@Valid @RequestBody UserDto userDto, @PathVariable Integer userId){
		UserDto updateUser = this.service.updateUser(userDto, userId);
		return new ResponseEntity<UserDto>(updateUser, HttpStatus.OK);
	}
	
	@GetMapping("/getUserById/{userId}")
	public ResponseEntity<UserDto> getUserById(@PathVariable Integer userId){
		UserDto user = this.service.getUserById(userId);
		return new ResponseEntity<UserDto>(user, HttpStatus.FOUND);
	}
	
	@GetMapping("/getAllUsers")
	public ResponseEntity<List<UserDto>> getAllUsers(){
		List<UserDto> allUsers = this.service.getAllUsers();
		return new ResponseEntity<List<UserDto>>(allUsers, HttpStatus.FOUND);
	}
	
	@DeleteMapping("/deleteUser/{userId}")
	public ResponseEntity<?> deleteUser(@PathVariable Integer userId){
		this.service.deleteUser(userId);
		return new ResponseEntity<Object>(Map.of("Message" , "User With User ID : {" + userId + "} Deleted Syccessfully"), HttpStatus.OK);

	}
}

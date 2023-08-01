package com.singh.rdst.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.singh.rdst.payloads.JwtAuthRequest;
import com.singh.rdst.payloads.JwtAuthResponse;
import com.singh.rdst.payloads.UserDto;
import com.singh.rdst.security.JwtTokenHelper;
import com.singh.rdst.services.UserService;

@RestController
@RequestMapping("/auth")
public class AuthController {
	
	@Autowired
	private JwtTokenHelper jwtTokenHelper;
	
	@Autowired
	private UserDetailsService userDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager; // for authenticate password
	
	@Autowired
	private UserService userService;

	@PostMapping("/login")
	public ResponseEntity<JwtAuthResponse> loginToCreateToken(
			@RequestBody JwtAuthRequest request
			){
		this.authenticateUsernameAndPassword(request.getUsername(), request.getPassword());
		// above method checks the username and password if correct then we can create token as below
		// by calling this.jwtTokenHelper.generateToken(null);// but for this we need user details(username)
		// first we get user details as below the generate token
		
		UserDetails userDetails = this.userDetailsService.loadUserByUsername(request.getUsername());
		
		String token = this.jwtTokenHelper.generateToken(userDetails);
		
		// Now we need to send this token
		
		JwtAuthResponse authResponse = new JwtAuthResponse();
		authResponse.setToken(token);
		
		return new ResponseEntity<JwtAuthResponse>(authResponse, HttpStatus.OK);
		
	}

	private void authenticateUsernameAndPassword(String username, String password) {
		
		UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(username,password);
		try {
			this.authenticationManager.authenticate(authenticationToken);
		} catch (DisabledException e) {
			System.out.println("User Is Disable...");
		}
	}
	
	// Register New User API
	@PostMapping("/registerNewUser")
	public ResponseEntity<UserDto> registerNewUser(@RequestBody UserDto userDto){
		UserDto newUser = this.userService.registerNewUser(userDto);
		return new ResponseEntity<UserDto>(newUser, HttpStatus.CREATED);
	}
	
}

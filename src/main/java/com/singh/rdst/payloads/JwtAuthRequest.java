package com.singh.rdst.payloads;

import lombok.Data;

@Data
public class JwtAuthRequest {
	
	private String username; // this is email id // user in post man as credentials
	private String password;
	
}

package com.singh.rdst.payloads;

import lombok.Data;

@Data
public class JwtAuthResponse {

	private String token;
	
	// This class will return only token 
	// Used Setter and Getter
}

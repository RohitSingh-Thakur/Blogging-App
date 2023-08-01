package com.singh.rdst.payloads;

import java.util.HashSet;
import java.util.Set;

import javax.validation.constraints.NotBlank;  
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import com.singh.rdst.entity.Role;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Integer userId;
	
	@NotBlank(message = "User Name Should Not Be Empty..")
	@Size(min = 2, max = 100, message = "Name Should Be Between 2 To 100 Charachters")
	@Pattern(regexp = "^([A-Za-z]+ )+[A-Za-z]+$|^[A-Za-z]+$", message = "User Name Should Not Contains Digits & Spaces DoesNot Allowed At Start And End Of the Word")
	// This REGEX Will Accept Only Characters Not Digits And Also Does Not Allow space at starting and ending
	// only accept only single space between 2 words
	private String userName;
	
	@NotBlank(message = "Email Id Should Not Be Empty..")
	@Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", 
				message = "Please Enter Valid Email ID")
	private String userEmail;
	
	@NotBlank(message = "Password Should Not Be Empty..")
	private String password;
	
	@NotBlank(message = "About Should Not Be Empty..")
	@Size(min = 2, max = 100, message = "About Should Be Between 2 To 100 Charachters")
	private String about;
	
	private Set<RoleDto> role = new HashSet<>();
}

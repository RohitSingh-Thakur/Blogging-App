package com.singh.rdst.payloads;

import javax.validation.constraints.NotBlank;  
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {
	
	private Integer user_Id;
	
	@NotBlank(message = "User Name Should Not Be Empty..")
	@Size(min = 2, max = 100, message = "Name Should Be Between 2 To 100 Charachters")
	@Pattern(regexp = "^([A-Za-z]+ )+[A-Za-z]+$|^[A-Za-z]+$", message = "User Name Should Not Contains Digits & Spaces DoesNot Allowed At Start And End Of the Word")
	// This REGEX Will Accept Only Characters Not Digits And Also Does Not Allow space at starting and ending
	// only accept only single space between 2 words
	private String user_Name;
	
	@NotBlank(message = "Email Id Should Not Be Empty..")
	@Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", 
				message = "Please Enter Valid Email ID")
	private String user_Email;
	
	@NotBlank(message = "Password Should Not Be Empty..")
	private String password;
	
	@NotBlank(message = "About Should Not Be Empty..")
	@Size(min = 2, max = 100, message = "About Should Be Between 2 To 100 Charachters")
	private String about;
}

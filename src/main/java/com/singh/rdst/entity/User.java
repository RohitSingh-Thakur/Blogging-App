package com.singh.rdst.entity;

import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Length;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "User_Table")
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer user_Id;
	
	@Column(nullable = false,unique = true)
	private String user_Name;
	
	@Column(nullable = false,unique = true)
	private String user_Email;
	
	@Column(nullable = false,unique = true)
	private String password;
	
	@Column(nullable = false,unique = true)
	private String about;
	
}

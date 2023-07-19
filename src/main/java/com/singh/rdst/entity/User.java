package com.singh.rdst.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity; 
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
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
	private Integer userId;
	
	@Column(nullable = false,unique = true)
	private String userName;
	
	@Column(nullable = false,unique = true)
	private String userEmail;
	
	@Column(nullable = false,unique = true)
	private String password;
	
	@Column(nullable = false,unique = true)
	private String about;
	
	@Column(nullable = false,unique = true)
	@OneToMany(mappedBy = "user", cascade = CascadeType.ALL)
	private List<Post> posts;
}

package com.singh.rdst.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Post_Table")
public class Post {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer postId;
	
	@Column(nullable = false,unique = true)
	private String title;
	
	@Column(nullable = false,unique = true)
	private String content;
	
	@Column(nullable = false,unique = true)
	private String imageName;
	
	@Column(nullable = false,unique = true)
	private Date addedDate;
	
	@ManyToOne
	@JoinColumn(name = "Categoty_ID")
	private Category category;
	
	@ManyToOne
	@JoinColumn(name = "User_ID")
	private User user;
}
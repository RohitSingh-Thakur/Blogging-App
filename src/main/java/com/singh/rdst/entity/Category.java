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

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Category_Table")
public class Category {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer categoryId;
	
	@Column(name = "title", length = 50, nullable = false, unique = true)
	private String categoryTitle;
	
	@Column(name = "description", length = 100, nullable = false, unique = true)
	private String categoryDiscription;
	
	@Column(nullable = false,unique = true)
	@OneToMany(mappedBy = "category", cascade = CascadeType.ALL)
	private List<Post> posts;
}

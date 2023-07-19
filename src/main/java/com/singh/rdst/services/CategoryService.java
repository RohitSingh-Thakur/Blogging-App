package com.singh.rdst.services;

import java.util.List;

import com.singh.rdst.payloads.CategoryDto;

public interface CategoryService {

	//Create
	CategoryDto createCategory(CategoryDto categoryDto);
	
	//Update
	CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//Get
	CategoryDto getCategory(Integer categoryId);
	
	//GetAll
	List<CategoryDto> getAllCategory();
	
	//Delete
	void deleteCategory(Integer categoryId);
	
}

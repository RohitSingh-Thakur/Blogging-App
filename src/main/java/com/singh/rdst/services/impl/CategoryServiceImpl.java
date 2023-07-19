package com.singh.rdst.services.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singh.rdst.entity.Category;
import com.singh.rdst.exceptions.ResourceNotFoundException;
import com.singh.rdst.payloads.CategoryDto;
import com.singh.rdst.repository.CategoryRepo;
import com.singh.rdst.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {
	
	@Autowired
	private CategoryRepo categoryRepo;
	
	@Autowired
	private ModelMapper mapper;

	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		Category category = this.mapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepo.save(category);
		return this.mapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(() -> 
		new ResourceNotFoundException("Category", "Id", categoryId));
		
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDiscription(categoryDto.getCategoryDiscription());
		
		Category updatedCategory = this.categoryRepo.save(category);
		return this.mapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto getCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->
		new ResourceNotFoundException("Category", "Id", categoryId));
		return this.mapper.map(category, CategoryDto.class);
	}

	@Override
	public List<CategoryDto> getAllCategory() {
		List<Category> allCategories = this.categoryRepo.findAll();
		return allCategories.stream().
			map(eachCategory -> 
				this.mapper.map(eachCategory, CategoryDto.class))
					.collect(Collectors.toList());
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		 Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> 
			new ResourceNotFoundException("Category", "Id", categoryId));
		 this.categoryRepo.delete(category);	 
	}

}

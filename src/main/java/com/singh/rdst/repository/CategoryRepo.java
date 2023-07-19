package com.singh.rdst.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.singh.rdst.entity.Category;

public interface CategoryRepo extends JpaRepository<Category, Integer>{

}

package com.singh.rdst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.singh.rdst.entity.Category;
import com.singh.rdst.entity.Post;
import com.singh.rdst.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
}

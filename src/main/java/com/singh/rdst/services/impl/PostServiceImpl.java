package com.singh.rdst.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singh.rdst.entity.Category;
import com.singh.rdst.entity.Post;
import com.singh.rdst.entity.User;
import com.singh.rdst.exceptions.ResourceNotFoundException;
import com.singh.rdst.payloads.PostDto;
import com.singh.rdst.repository.CategoryRepo;
import com.singh.rdst.repository.PostRepo;
import com.singh.rdst.repository.UserRepo;
import com.singh.rdst.services.PostService;

@Service
public class PostServiceImpl implements PostService {
	
	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private CategoryRepo categoryRepo;

	@Override
	public PostDto createPost(PostDto postDto,Integer userId, Integer categoryId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User", "ID", userId));
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()-> new ResourceNotFoundException("Category", "ID", categoryId));
		
		Post post = this.mapper.map(postDto, Post.class);
		post.setImageName("default.png");
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.postRepo.save(post);
		return this.mapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<PostDto> getAllPosts() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PostDto getPostByPostId(Integer postId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<PostDto> searchPosts(String keywords) {
		// TODO Auto-generated method stub
		return null;
	}

}

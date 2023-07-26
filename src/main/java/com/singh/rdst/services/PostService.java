package com.singh.rdst.services;

import java.util.List;

import com.singh.rdst.entity.Post;
import com.singh.rdst.payloads.PostDto;
import com.singh.rdst.payloads.PostResponse;

public interface PostService {
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId); // Create Post
	PostDto updatePost(PostDto postDto, Integer postId); // Update Post
	void deletePost(Integer postId); // Delete Post
	List<PostDto> getAllPosts(); // Get All Posts
	
	//Pagination
	PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir); // Get All Posts
	
	
	PostDto getPostByPostId(Integer postId); // Get Single Post By PostId
	List<PostDto> getAllPostsByCategory(Integer categoryId); // Get All Post By CategoryID
	List<PostDto> getAllPostsByUser(Integer userId); // Get All Posts By UserId
	
	
	List<PostDto> searchPosts(String keywords); // Search Post By Test
	
	List<PostDto> searchMyPostByTitile(String keywords);
}

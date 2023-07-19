package com.singh.rdst.services;

import java.util.List;

import com.singh.rdst.entity.Post;
import com.singh.rdst.entity.User;
import com.singh.rdst.payloads.PostDto;

public interface PostService {
	
	PostDto createPost(PostDto postDto, Integer userId, Integer categoryId); // Create Post
	PostDto updatePost(PostDto postDto, Integer postId); // Update Post
	void deletePost(Integer postId); // Delete Post
	List<PostDto> getAllPosts(); // Get All Posts
	PostDto getPostByPostId(Integer postId); // Get Single Post By PostId
	List<PostDto> getAllPostsByCategory(Integer categoryId); // Get All Post By CategoryID
	List<PostDto> getAllPostsByUser(Integer userId); // Get All Posts By UserId
	List<PostDto> searchPosts(String keywords); // Search Post By Test
}

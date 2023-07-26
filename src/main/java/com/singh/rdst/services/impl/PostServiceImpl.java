package com.singh.rdst.services.impl;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.singh.rdst.entity.Category;
import com.singh.rdst.entity.Post;
import com.singh.rdst.entity.User;
import com.singh.rdst.exceptions.ResourceNotFoundException;
import com.singh.rdst.payloads.CategoryDto;
import com.singh.rdst.payloads.PostDto;
import com.singh.rdst.payloads.PostResponse;
import com.singh.rdst.payloads.UserDto;
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
		post.setAddedDate(new Date());
		post.setUser(user);
		post.setCategory(category);
		
		Post savedPost = this.postRepo.save(post);
		return this.mapper.map(savedPost, PostDto.class);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post Id", postId));
		
		post.setTitle(postDto.getTitle());
		post.setContent(postDto.getContent());
		post.setImageName(postDto.getImageName());
		
		Post updatedPost = this.postRepo.save(post);
		PostDto updatedPostDto = this.mapper.map(updatedPost, PostDto.class);

		return updatedPostDto;
	}

	@Override
	public void deletePost(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "Post ID", postId));
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDto> getAllPosts() {
		List<Post> allPosts = this.postRepo.findAll();
		List<PostDto> allPostsDto = allPosts.stream().map(e -> this.mapper.map(e, PostDto.class)).collect(Collectors.toList());
		return allPostsDto;
	}
	
	// Pagination 
	@Override
	public PostResponse getAllPosts(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		
//		Sort sort = null;
//		if(sortDir.equalsIgnoreCase("asc")) {
//			sort = Sort.by(sortBy).ascending();
//		}else {
//			sort = Sort.by(sortBy).descending();
//		}
		
		// By Using Ternary Operator
		
		Sort sort = (sortDir.equalsIgnoreCase("asc"))?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		
		
		Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePost = this.postRepo.findAll(pageRequest);
		List<Post> allPosts = pagePost.getContent();
		List<PostDto> allPostsDto = allPosts.stream().map(e -> this.mapper.map(e, PostDto.class)).collect(Collectors.toList());
		
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(allPostsDto);
		postResponse.setPageNumber(pagePost.getNumber());
		postResponse.setPageSize(pagePost.getSize());
		postResponse.setTotalElements(pagePost.getTotalElements());
		postResponse.setTotalPages(pagePost.getTotalPages());
		postResponse.setLastPage(pagePost.isLast());
		
		
		return postResponse;
	}

	@Override
	public PostDto getPostByPostId(Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "PostID", postId));
		PostDto postDto = this.mapper.map(post, PostDto.class);
		return postDto;
	}

	@Override
	public List<PostDto> getAllPostsByCategory(Integer categoryId) {
		Category category = this.categoryRepo.findById(categoryId).orElseThrow(()->
		new ResourceNotFoundException("Category", "Id", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		
		List<PostDto> postsDto = posts.stream().map(e -> this.mapper.map(e, PostDto.class)).collect(Collectors.toList());
		
		return postsDto;
	}

	@Override
	public List<PostDto> getAllPostsByUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()->
		new ResourceNotFoundException("User", "Id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		
		List<PostDto> postsDto = posts.stream().map(e -> this.mapper.map(e, PostDto.class)).collect(Collectors.toList());
		
		return postsDto;
	}

	@Override
	public List<PostDto> searchPosts(String keywords) {
		List<Post> posts = this.postRepo.findByTitleContaining(keywords);
		List<PostDto> postList = posts.stream().map((post) -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postList;
	}

	@Override
	public List<PostDto> searchMyPostByTitile(String keywords) {
		List<Post> postsByTitile = this.postRepo.searchMyPostByTitile("%"+keywords+"%");
		List<PostDto> postsDtoByTitile = postsByTitile.stream().map((post) -> this.mapper.map(post, PostDto.class)).collect(Collectors.toList());
		return postsDtoByTitile;
	}

}

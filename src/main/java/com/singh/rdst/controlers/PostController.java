package com.singh.rdst.controlers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.singh.rdst.exceptions.ResourceNotFoundException;
import com.singh.rdst.payloads.PostDto;
import com.singh.rdst.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@PostMapping("/createPost/user/{userId}/category/{categoryId}")
	public ResponseEntity<PostDto> createPost(@Valid @RequestBody PostDto postDto, @PathVariable Integer userId, @PathVariable Integer categoryId){
		PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		return new ResponseEntity<PostDto>(createPost, HttpStatus.CREATED);
	}
	
	@GetMapping("/getAllPostsByUserId/{userId}") 
	public ResponseEntity<List<PostDto>> getAllPostsByUserID(@PathVariable Integer userId){
		
		List<PostDto> allPostsByUser = this.postService.getAllPostsByUser(userId);
			return new ResponseEntity<List<PostDto>>(allPostsByUser, HttpStatus.FOUND);
	}
	
	@GetMapping("/getAllPostsByCategoryId/{categoryId}") 
	public ResponseEntity<List<PostDto>> getAllPostsByCategoryId(@PathVariable Integer categoryId){
		
		List<PostDto> allPostsByUser = this.postService.getAllPostsByCategory(categoryId);
			return new ResponseEntity<List<PostDto>>(allPostsByUser, HttpStatus.FOUND);
	}
	
	@GetMapping("/getAllPosts")
	public ResponseEntity<List<PostDto>> getAllPosts(){
		List<PostDto> allPosts = this.postService.getAllPosts();
		if(!allPosts.isEmpty()) {
			return new ResponseEntity<List<PostDto>>(allPosts, HttpStatus.OK);
		}else {
			throw new ResourceNotFoundException("No Posts Awailable...", "Posts", null);
		}
	}
	
	@GetMapping("/getPostByPostId/{postId}")
	public ResponseEntity<PostDto> getPostByPostId(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostByPostId(postId);
			return new ResponseEntity<PostDto>(postDto, HttpStatus.FOUND);
	}
	
	@DeleteMapping("/deletePostByPostId/{postId}")
	public ResponseEntity<String> deletePost(@PathVariable Integer postId){
		this.postService.deletePost(postId);
		return new ResponseEntity<String>("Post Deleted Successfully...", HttpStatus.OK);
	}
	
	@PutMapping("/updatePost/{postId}")
	public ResponseEntity<PostDto> updatePost(@Valid @RequestBody PostDto postDto, @PathVariable Integer postId){
		PostDto updatePostDto = this.postService.updatePost(postDto, postId);
			return new ResponseEntity<PostDto>(updatePostDto, HttpStatus.OK);
	}
}

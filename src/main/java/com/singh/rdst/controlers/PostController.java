package com.singh.rdst.controlers;

import java.io.IOException;
import java.util.List; 

import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.singh.rdst.config.AppConstants;
import com.singh.rdst.exceptions.ResourceNotFoundException;
import com.singh.rdst.payloads.PostDto;
import com.singh.rdst.payloads.PostResponse;
import com.singh.rdst.services.FileService;
import com.singh.rdst.services.PostService;

@RestController
@RequestMapping("/api/posts")
public class PostController {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
	
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
	// Pagination
	@GetMapping("/getAllPostsPagination")
	public ResponseEntity<PostResponse> getAllPostsPagination(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber, // PageNumber Starts From 0
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortDir", defaultValue = AppConstants.SORT_DIR, required = false) String sortDir
			){
		PostResponse allPostsPagination = this.postService.getAllPosts(pageNumber,pageSize,sortBy,sortDir);
		if(allPostsPagination != null) {
			return new ResponseEntity<PostResponse>(allPostsPagination, HttpStatus.OK);
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
	
	@GetMapping("/searchPostByTitle/{keywords}")
	public ResponseEntity<List<PostDto>> searchPosts(@PathVariable String keywords){
		List<PostDto> posts = this.postService.searchPosts(keywords);
		if(!posts.isEmpty()) {
			return new ResponseEntity<List<PostDto>>(posts, HttpStatus.OK);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	@GetMapping("/searchMyPostByTitile/{keywords}")
	public ResponseEntity<List<PostDto>> searchMyPostByTitile(@PathVariable String keywords){
		List<PostDto> searchMyPostByTitile = this.postService.searchMyPostByTitile(keywords);
		if(!searchMyPostByTitile.isEmpty()) {
			return new ResponseEntity<List<PostDto>>(searchMyPostByTitile, HttpStatus.FOUND);
		}else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
	
	// Image Upload Code PostImageUpload
	@PostMapping("/uploadPostImage/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
			@RequestParam("myImage") MultipartFile file,
			@PathVariable Integer postId
			) throws IOException{
		PostDto postDto = this.postService.getPostByPostId(postId);
		String fileName = this.fileService.uploadImage(path, file);
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost, HttpStatus.OK);
		}
	
}

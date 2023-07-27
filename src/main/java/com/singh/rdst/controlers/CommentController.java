package com.singh.rdst.controlers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.singh.rdst.payloads.CommentDto;
import com.singh.rdst.services.CommentService;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
	
	@Autowired
	private CommentService service;
	
	@PostMapping("/createComment/{postId}")
	public ResponseEntity<CommentDto> createComment(@RequestBody CommentDto commentDto, @PathVariable Integer postId){
		CommentDto createComment = this.service.createComment(commentDto, postId);
		return new ResponseEntity<CommentDto>(createComment, HttpStatus.CREATED);
	}
	
	@DeleteMapping("/deleteComment/{commentId}")
	public ResponseEntity<String> deleteComment(@PathVariable Integer commentId){
		this.service.deleteComment(commentId);
		return new ResponseEntity<String>("comment deleted", HttpStatus.OK);
	}
}

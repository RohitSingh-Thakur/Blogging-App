package com.singh.rdst.services.impl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.singh.rdst.entity.Comment;
import com.singh.rdst.entity.Post;
import com.singh.rdst.exceptions.ResourceNotFoundException;
import com.singh.rdst.payloads.CommentDto;
import com.singh.rdst.repository.CommentRepo;
import com.singh.rdst.repository.PostRepo;
import com.singh.rdst.services.CommentService;

@Service
public class CommentServiceImpl implements CommentService {

	@Autowired
	private PostRepo postRepo;
	
	@Autowired
	private CommentRepo commentRepo;
	
	@Autowired
	private ModelMapper mapper;
	
	@Override
	public CommentDto createComment(CommentDto commentDto, Integer postId) {
		Post post = this.postRepo.findById(postId).orElseThrow(() -> new ResourceNotFoundException("Post", "post id", postId));
		Comment comment = this.mapper.map(commentDto, Comment.class);
		comment.setPost(post);
		Comment savedComment = this.commentRepo.save(comment);
		CommentDto commentDto2 = this.mapper.map(savedComment, CommentDto.class);
		return commentDto2;
	}

	@Override
	public void deleteComment(Integer commentId) {
		Comment comment = this.commentRepo.findById(commentId).orElseThrow(() -> new ResourceNotFoundException("Comment", "comment id", commentId));
		this.commentRepo.delete(comment);

	}

}

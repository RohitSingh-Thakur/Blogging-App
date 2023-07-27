package com.singh.rdst.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.singh.rdst.entity.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer> {

}

package com.singh.rdst.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.singh.rdst.entity.Category;
import com.singh.rdst.entity.Post;
import com.singh.rdst.entity.User;

public interface PostRepo extends JpaRepository<Post, Integer>{

	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);//findBy<FieldName>Containing -> It Will Generate like query on given field
	
	// Alternative Of Above Method
	@Query("select p from Post p where p.title like :keywords") // here : is for dynamic value and keywords is a parameter
	List<Post> searchMyPostByTitile(@Param("keywords") String title);
}

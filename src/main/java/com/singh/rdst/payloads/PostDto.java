package com.singh.rdst.payloads;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PostDto {

	private Integer postId;

	@NotBlank(message = "Title Should Not Be Empty..")
	@Size(min = 2, max = 50, message = "Title Should Be Between 2 To 100 Charachters")
	private String title;

	@NotBlank(message = "Content Should Not Be Empty..")
	@Size(min = 2, max = 100, message = "Content Should Be Between 2 To 100 Charachters")
	private String content;

	@NotBlank(message = "Please Insert Image")
	private String imageName;

	@DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
	@JsonFormat(pattern = "yyyy/MM/dd")
	private Date addedDate;

	private CategoryDto category;
	private UserDto user;

	private List<CommentDto> comments = new ArrayList<CommentDto>();
}

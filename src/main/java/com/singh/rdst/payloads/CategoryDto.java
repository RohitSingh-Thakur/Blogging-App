package com.singh.rdst.payloads;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CategoryDto {

	private Integer categoryId;
	
	@NotBlank(message = "Title Should Not Be Empty..")
	@Size(min = 2, max = 50, message = "Title Should Be Between 2 To 100 Charachters")
	private String categoryTitle;
	
	@NotBlank(message = "Description Should Not Be Empty..")
	@Size(min = 2, max = 100, message = "Description Should Be Between 2 To 100 Charachters")
	private String categoryDiscription;
}

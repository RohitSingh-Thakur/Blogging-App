package com.singh.rdst.exceptions;

import lombok.Data;

@Data
public class ResourceNotFoundException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	String resourceName;
	String fieldName;
	Integer fieldValue;
	
	
	public ResourceNotFoundException(String resourceName, String fieldName,Integer fieldValue) {
		super(String.format("%s Not Found With %s : %s", resourceName, fieldName, fieldValue));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
	
	

}

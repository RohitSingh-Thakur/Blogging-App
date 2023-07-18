package com.singh.rdst.exceptions;

import java.sql.SQLIntegrityConstraintViolationException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<?> haldleResourceNotFoundException(ResourceNotFoundException ex){
		String message = ex.getMessage();
		return new ResponseEntity<Object>(message, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		Map<String, String> errorMap = new HashMap<>();
		
		ex.getBindingResult().getAllErrors().forEach((error) -> {
			String errorFiled = ((FieldError)error).getField();
			String errorMessage = error.getDefaultMessage();
			errorMap.put(errorFiled, errorMessage);
		});
		return new ResponseEntity<Map<String,String>>(errorMap,HttpStatus.BAD_REQUEST);
	}
	
	/* This method is created to handle exception while entering duplicate entry in DB*/
	@ExceptionHandler(SQLIntegrityConstraintViolationException.class)
	public ResponseEntity<Map<String,String>> handleSQLIntegrityConstraintViolationException(SQLIntegrityConstraintViolationException ex){
		Map<String,String> list = new HashMap<String,String>();
		list.put("Duplicate User Found..",ex.getMessage());//Duplicate entry 'I am a java developer' for key 'user_table.UK_iq4q01o97yl4lp7rwj4c3il2m'
		return new ResponseEntity<Map<String,String>>(list,HttpStatus.BAD_REQUEST);
	}
}
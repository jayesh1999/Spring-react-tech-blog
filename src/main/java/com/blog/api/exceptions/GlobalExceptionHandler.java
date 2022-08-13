package com.blog.api.exceptions;

import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.blog.api.payloads.ApiResponse;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<ApiResponse> RespourceNotFoundExceptionHandler(ResourceNotFoundException ex){
		String message =  ex.getMessage();
		ApiResponse api = new ApiResponse(message,false);
		
		return new ResponseEntity<ApiResponse>(api,HttpStatus.NOT_FOUND);
		
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String,String>> handleMethodArgumentNotValidException(MethodArgumentNotValidException ex){
		
		Map<String,String> resp = new HashMap<>();
		ex.getBindingResult().getAllErrors().forEach((error)->{
			String fieldName = ((FieldError)error).getField();
			String message =  error.getDefaultMessage();
			
			resp.put(fieldName, message);
		});
		
		return new ResponseEntity<>(resp,HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(ApiException.class)
	public ResponseEntity<ApiResponse> handleApiException(ApiException ex){
		
		String meessage = ex.getMessage();
		ApiResponse exceptions  = new ApiResponse(meessage,true);
		System.out.println(exceptions);
		return new ResponseEntity<ApiResponse>(exceptions,HttpStatus.BAD_REQUEST);
		
	}
}

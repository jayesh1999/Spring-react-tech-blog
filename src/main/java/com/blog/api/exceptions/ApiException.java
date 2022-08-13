package com.blog.api.exceptions;

public class ApiException extends RuntimeException{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -377877681559409221L;
	private String message;
	public ApiException() {
		super();
		// TODO Auto-generated constructor stub
	}
	public ApiException(String message) {
		super(message);
		this.message = message;
	}



	
	

}

package com.dreamfitbackend.configs.exceptions;

import org.springframework.http.HttpStatus;

public class MessageException extends RuntimeException{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpStatus status;
	
	public MessageException(String message, HttpStatus status) {
		super(message);
		this.status = status;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}
	
}

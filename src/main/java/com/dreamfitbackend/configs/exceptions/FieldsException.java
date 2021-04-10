package com.dreamfitbackend.configs.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.dreamfitbackend.configs.exceptions.Problem.Field;

public class FieldsException extends RuntimeException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private HttpStatus status;
	
	private List<Field> fields;
	
	public FieldsException(HttpStatus status, List<Field> fields) {
		super("Campos inválidos");
		this.status = status;
		this.fields = fields;
	}

	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}


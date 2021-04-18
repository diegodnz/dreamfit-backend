package com.dreamfitbackend.configs.exceptions;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.dreamfitbackend.configs.exceptions.Problem.Field;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(allowGetters = false, allowSetters = false)
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

	@JsonIgnore(value = false)
	public HttpStatus getStatus() {
		return status;
	}

	public void setStatus(HttpStatus status) {
		this.status = status;
	}

	@JsonIgnore(value = false)
	public List<Field> getFields() {
		return fields;
	}

	public void setFields(List<Field> fields) {
		this.fields = fields;
	}
}


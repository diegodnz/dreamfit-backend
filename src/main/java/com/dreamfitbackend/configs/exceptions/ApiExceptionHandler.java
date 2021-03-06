package com.dreamfitbackend.configs.exceptions;

import java.time.OffsetDateTime;
import java.util.ArrayList;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.MaxUploadSizeExceededException;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dreamfitbackend.configs.generalDtos.StatusMessage;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@Autowired
	private MessageSource messageSource;
	
	@Value("${spring.servlet.multipart.max-file-size}")
	private String maxFileSize;
	
	@ExceptionHandler(MessageException.class)	
	public ResponseEntity<Object> handleMessage(MessageException ex, WebRequest request) {
		HttpStatus status = ex.getStatus();	

		StatusMessage statusMessage = new StatusMessage(status.value(), ex.getMessage());		
		
		return handleExceptionInternal(ex, statusMessage, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(FieldsException.class)
	public ResponseEntity<Object> handleFields(FieldsException ex, WebRequest request) {
		HttpStatus status = ex.getStatus();	

		Problem problem = new Problem();
		problem.setStatus(status.value());
		problem.setTitle("Um ou mais campos est??o invalidos");
		problem.setDateTime(OffsetDateTime.now());
		problem.setFields(ex.getFields());		
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}
	
	@ExceptionHandler(ConstraintViolationException.class)
	public ResponseEntity<Object> handleValidation(ConstraintViolationException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;	
	
		Problem problem = new Problem();
		problem.setStatus(status.value());
		problem.setTitle(ex.getMessage());
		problem.setDateTime(OffsetDateTime.now());		
		
		return handleExceptionInternal(ex, problem, new HttpHeaders(), status, request);
	}

	
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
		
		
		ArrayList<Problem.Field> fields = new ArrayList<Problem.Field>();
		for (ObjectError error : ex.getBindingResult().getAllErrors()) {
			String name = ((FieldError)error).getField();
			String message = messageSource.getMessage(error, LocaleContextHolder.getLocale());
			fields.add(new Problem.Field(name, message));
		}
		
		Problem problem = new Problem();
		problem.setStatus(status.value());
		problem.setTitle("Um ou mais campos est??o invalidos");
		problem.setDateTime(OffsetDateTime.now());	
		
		problem.setFields(fields);
		
		return super.handleExceptionInternal(ex, problem, headers, status, request);
	}
	
	@ExceptionHandler(MultipartException.class)
	public ResponseEntity<Object> handleFileLimit(MultipartException ex, WebRequest request) {
		HttpStatus status = HttpStatus.BAD_REQUEST;
		
		Integer maxSize = Integer.parseInt(maxFileSize.substring(0, maxFileSize.indexOf('B')-1));
		String maxSizeStr = String.valueOf(maxSize) + maxFileSize.subSequence(maxFileSize.indexOf('B')-1, maxFileSize.indexOf('B')+1);
		StatusMessage response = new StatusMessage(status.value(), "A imagem excede o limite de " + maxSizeStr);
		
		return handleExceptionInternal(ex, response, new HttpHeaders(), status, request);
	}
}
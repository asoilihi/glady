package com.backend.as.glady.controllers.handler;

import javax.persistence.EntityNotFoundException;

import org.hibernate.exception.ConstraintViolationException;
import org.springframework.data.crossstore.ChangeSetPersister.NotFoundException;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

/**
 * @author asoilihi
 *
 */
@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	/**
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { IllegalArgumentException.class, InvalidFormatException.class})
	protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
		ResponseError bodyOfResponse = ResponseError.builder().status(HttpStatus.BAD_REQUEST.value()).message(ex.getMessage()).build();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
	}
	
	/**
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { ConstraintViolationException.class})
	protected ResponseEntity<Object> handleViolation(RuntimeException ex, WebRequest request) {
		ResponseError bodyOfResponse = ResponseError.builder().status(HttpStatus.CONFLICT.value()).message(ex.getMessage()).build();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
	}

	/**
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { NotFoundException.class, EntityNotFoundException.class })
	protected ResponseEntity<Object> handleNotFoundException(RuntimeException ex, WebRequest request) {
		ResponseError bodyOfResponse = ResponseError.builder().status(HttpStatus.NOT_FOUND.value()).message(ex.getMessage()).build();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_FOUND, request);
	}
	
	/**
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { UnsupportedOperationException.class })
	protected ResponseEntity<Object> handleUnsupportedOperationException(RuntimeException ex, WebRequest request) {
		ResponseError bodyOfResponse = ResponseError.builder().status(HttpStatus.NOT_ACCEPTABLE.value()).message(ex.getMessage()).build();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.NOT_ACCEPTABLE, request);
	}
	
	
	/**
	 * @param ex
	 * @param request
	 * @return
	 */
	@ExceptionHandler(value = { Exception.class })
	protected ResponseEntity<Object> handleOthersException(RuntimeException ex, WebRequest request) {
		ResponseError bodyOfResponse = ResponseError.builder().status(HttpStatus.INTERNAL_SERVER_ERROR.value()).message(ex.getMessage()).build();
		return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
	}
	
	
}
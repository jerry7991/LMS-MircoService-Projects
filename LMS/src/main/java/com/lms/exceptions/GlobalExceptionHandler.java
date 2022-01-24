package com.lms.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.method.HandlerMethod;

import com.lms.util.Constants;

import feign.FeignException;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BookAlreadyIssuedException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, Object> bookAlreadyIssuedException(BookAlreadyIssuedException bookAlreadyIssuedException,
			HandlerMethod handlerMethod) {
		Map<String, Object> response = new HashMap<>();
		response.put(Constants.SERVICE, handlerMethod.getMethod().getDeclaringClass());
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, bookAlreadyIssuedException.getMessage());
		response.put(Constants.STATUS, HttpStatus.CONFLICT.name());
		return response;
	}

	@ExceptionHandler(IssuedBookOutOfBoundException.class)
	@ResponseStatus(HttpStatus.BAD_REQUEST)
	public Map<String, Object> issuedBookOutOfBoundException(
			IssuedBookOutOfBoundException issuedBookOutOfBoundException, HandlerMethod handlerMethod) {
		Map<String, Object> response = new HashMap<>();
		response.put(Constants.SERVICE, handlerMethod.getMethod().getDeclaringClass());
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, issuedBookOutOfBoundException.getMessage());
		response.put(Constants.STATUS, HttpStatus.BAD_REQUEST.name());
		return response;
	}

	@ExceptionHandler(HttpClientErrorException.class)
	public ResponseEntity<String> preValidateControllers(HttpClientErrorException ex) {
		return ResponseEntity.status(ex.getStatusCode()).body(ex.getResponseBodyAsString());
	}

	@ExceptionHandler(FeignException.class)
	public Map<String, Object> feignHandler(FeignException exception, HandlerMethod handlerMethod) {
		Map<String, Object> response = new HashMap<>();
		response.put(Constants.SERVICE, handlerMethod.getMethod().getDeclaringClass());
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, exception.getMessage());
		response.put(Constants.STATUS, exception.status());
		return response;
	}
}

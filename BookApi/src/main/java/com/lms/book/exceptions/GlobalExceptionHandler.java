package com.lms.book.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import com.lms.book.util.Constants;

@RestControllerAdvice
public class GlobalExceptionHandler {

	@ExceptionHandler(BookNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, Object> bookNotFoundException(BookNotFoundException bookNotFoundException,
			HandlerMethod handlerMethod) {
		Map<String, Object> response = new HashMap<>();
		response.put(Constants.SERVICE, handlerMethod.getMethod().getDeclaringClass());
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, bookNotFoundException.getMessage());
		response.put(Constants.STATUS, HttpStatus.NOT_FOUND.name());
		return response;
	}

	@ExceptionHandler(DuplicateBookException.class)
	@ResponseStatus(HttpStatus.CONFLICT)
	public Map<String, Object> duplicateBookException(DuplicateBookException duplicateBookException,
			HandlerMethod handlerMethod) {
		Map<String, Object> response = new HashMap<>();
		response.put(Constants.SERVICE, handlerMethod.getMethod().getDeclaringClass());
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, duplicateBookException.getMessage());
		response.put(Constants.STATUS, HttpStatus.CONFLICT.name());
		return response;
	}
}

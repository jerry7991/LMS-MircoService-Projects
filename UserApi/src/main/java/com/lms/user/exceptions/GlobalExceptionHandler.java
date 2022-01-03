package com.lms.user.exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.HandlerMethod;

import com.lms.user.util.Constants;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ResponseStatus(HttpStatus.CONFLICT)
	@ExceptionHandler(DuplicateUserException.class)
	public Map<String, Object> duplicateUserException(DuplicateUserException duplicateUserException,
			HandlerMethod handlerMethod) {
		Map<String, Object> response = new HashMap<>();
		response.put(Constants.SERVICE, handlerMethod.getMethod().getDeclaringClass());
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, duplicateUserException.getMessage());
		response.put(Constants.STATUS, HttpStatus.CONFLICT.name());
		return response;
	}

	@ExceptionHandler(UserNotFoundException.class)
	@ResponseStatus(HttpStatus.NOT_FOUND)
	public Map<String, Object> userNotFoundException(UserNotFoundException userNotFoundException,
			HandlerMethod handlerMethod) {
		Map<String, Object> response = new HashMap<>();
		response.put(Constants.SERVICE, handlerMethod.getMethod().getDeclaringClass());
		response.put(Constants.TIME_STAMP, new Date().toString());
		response.put(Constants.ERROR, userNotFoundException.getMessage());
		response.put(Constants.STATUS, HttpStatus.NOT_FOUND.name());
		return response;
	}
}

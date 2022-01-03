package com.lms.book.exceptions;

public class DuplicateBookException extends Exception {

	private static final long serialVersionUID = 1L;

	public DuplicateBookException(String message) {
		super(message);
	}

}

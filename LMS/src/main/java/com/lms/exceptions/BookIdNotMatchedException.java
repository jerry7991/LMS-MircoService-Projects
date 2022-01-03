package com.lms.exceptions;

public class BookIdNotMatchedException extends Exception {
	private static final long serialVersionUID = 1L;

	public BookIdNotMatchedException() {
		super("Book not yet issued to any users.");
	}
}

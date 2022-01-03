package com.lms.book.exceptions;

public class BookNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;

	private static final String ERR_MSG = "Book not found exception.";

	public BookNotFoundException() {
		super(ERR_MSG);
	}
}
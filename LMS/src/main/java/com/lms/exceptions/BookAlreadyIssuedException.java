package com.lms.exceptions;

public class BookAlreadyIssuedException extends Exception {
	private static final long serialVersionUID = 1L;

	public BookAlreadyIssuedException(String errMsg) {
		super(errMsg);
	}
}

package com.lms.exceptions;

public class IssuedBookOutOfBoundException extends Exception {
	private static final long serialVersionUID = 1L;

	public IssuedBookOutOfBoundException(String errMsg) {
		super(errMsg);
	}
}

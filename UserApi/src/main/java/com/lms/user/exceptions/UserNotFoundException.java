package com.lms.user.exceptions;

public class UserNotFoundException extends Exception {
	private static final long serialVersionUID = 1L;
	private static final String ERR_MSG = "User doesn't exit.";

	public UserNotFoundException() {
		super(ERR_MSG);
	}

	public UserNotFoundException(String errMsg) {
		super(errMsg);
	}
}

package com.pcms.users.Exception;

public class UserDoesNotExistsException extends RuntimeException {
	public UserDoesNotExistsException(String msg) {
		super(msg);
	}
}

package com.tasly.deepureflow.exception;

import org.apache.shiro.authc.AuthenticationException;

public class UserActiveException extends AuthenticationException {
	/**
	 * 
	 */
	private static final long serialVersionUID = 5285237615973109507L;

	public UserActiveException() {
	}

	public UserActiveException(String message) {
		super(message);
	}

	public UserActiveException(Throwable cause) {
		super(cause);
	}

	public UserActiveException(String message, Throwable cause) {
		super(message, cause);
	}
}

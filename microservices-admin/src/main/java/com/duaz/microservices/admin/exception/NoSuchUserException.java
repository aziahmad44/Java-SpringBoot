package com.duaz.microservices.admin.exception;

public class NoSuchUserException extends RintException {
	private static final long serialVersionUID = 1L;

	public NoSuchUserException() {
		super();
	}

	public NoSuchUserException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public NoSuchUserException(final String message) {
		super(message);
	}

	public NoSuchUserException(final Throwable cause) {
		super(cause);
	}


}

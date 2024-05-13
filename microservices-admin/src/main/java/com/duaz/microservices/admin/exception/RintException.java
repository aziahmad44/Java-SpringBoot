package com.duaz.microservices.admin.exception;

public class RintException extends Exception {
	private static final long serialVersionUID = 1L;

	public RintException() {
		super();
	}

	public RintException(final String message, final Throwable cause) {
		super(message, cause);
	}

	public RintException(final String message) {
		super(message);
	}

	public RintException(final Throwable cause) {
		super(cause);
	}


}

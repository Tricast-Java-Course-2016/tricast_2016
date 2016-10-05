package com.tricast.guice;

public class OutOfTransactionException extends RuntimeException {

	private static final long serialVersionUID = -5539565820915244534L;

	public OutOfTransactionException() {
	}

	public OutOfTransactionException(String message) {
		super(message);
	}

	public OutOfTransactionException(String message, Throwable cause) {
		super(message, cause);
	}

	public OutOfTransactionException(Throwable cause) {
		super(cause);
	}
}
package com.bbva.paas.gdd.exception;

public class RestException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public RestException(String message) {
		super();
		this.message = message;
	}

	/**
	 * Constructor
	 */
	public RestException() {
		super();
	}

	/**
	 * Constructor
	 * 
	 * @param message
	 *            Message
	 * @param cause
	 *            Class that can be thrown.
	 */
	public RestException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * 
	 * @param cause
	 *            Class that can be thrown.
	 */
	public RestException(Throwable cause) {
		super(cause);
	}

	@Override
	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}
}

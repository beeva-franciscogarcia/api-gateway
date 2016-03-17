package com.bbva.paas.gdd.exception;

public class ConfigurationException extends Exception {

	private static final long serialVersionUID = 1L;

	private String message;

	/**
	 * Constructor
	 * 
	 * @param message
	 */
	public ConfigurationException(String message) {
		super();
		this.message = message;
	}

	/**
	 * Constructor
	 */
	public ConfigurationException() {
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
	public ConfigurationException(String message, Throwable cause) {
		super(message, cause);
	}

	/**
	 * Constructor
	 * 
	 * @param cause
	 *            Class that can be thrown.
	 */
	public ConfigurationException(Throwable cause) {
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

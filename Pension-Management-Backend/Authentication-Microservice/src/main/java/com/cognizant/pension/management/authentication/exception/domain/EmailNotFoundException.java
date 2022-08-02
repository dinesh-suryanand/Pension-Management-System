package com.cognizant.pension.management.authentication.exception.domain;

/**
 * @author Adrish
 *
 */
public class EmailNotFoundException extends Exception {

	/**
	 * Exception thrown when email does not exist
	 */
	private static final long serialVersionUID = 1L;
	public EmailNotFoundException(String message) {
		super(message);
	}
}

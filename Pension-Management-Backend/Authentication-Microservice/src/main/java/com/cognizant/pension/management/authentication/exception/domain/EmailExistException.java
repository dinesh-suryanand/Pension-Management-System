package com.cognizant.pension.management.authentication.exception.domain;

/**
 * @author Adrish
 *
 */
public class EmailExistException extends Exception {

	/**
	 * Exception thrown when email is already in use
	 */
	private static final long serialVersionUID = 1L;
	public EmailExistException(String message) {
		super(message);
	}
}

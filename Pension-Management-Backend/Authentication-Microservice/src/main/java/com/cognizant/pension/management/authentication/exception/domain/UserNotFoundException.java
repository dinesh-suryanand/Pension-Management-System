package com.cognizant.pension.management.authentication.exception.domain;

/**
 * @author Adrish
 *
 */
public class UserNotFoundException extends Exception {

	/**
	 * Exception thrown if the username does not exist
	 */
	private static final long serialVersionUID = 1L;
	public UserNotFoundException(String message) {
		super(message);
	}
}

package com.cognizant.pension.management.authentication.exception.domain;

/**
 * @author Adrish
 *
 */
public class UsernameExistException extends Exception {

	/**
	 * Exception thrown if the username is already in use
	 */
	private static final long serialVersionUID = 1L;
	public UsernameExistException(String message) {
		super(message);
	}
}

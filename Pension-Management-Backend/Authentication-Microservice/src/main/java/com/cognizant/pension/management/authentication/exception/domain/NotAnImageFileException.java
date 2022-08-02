package com.cognizant.pension.management.authentication.exception.domain;

/**
 * @author Adrish
 *
 */
public class NotAnImageFileException extends Exception {

	/**
	 * Exception thrown if the file is not an image file
	 */
	private static final long serialVersionUID = 1L;
	public NotAnImageFileException(String message) {
		super(message);
	}
}

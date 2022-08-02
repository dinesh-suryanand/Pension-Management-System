package com.cognizant.process.pension.exception;

public class ProcessPensionException extends Exception {

	/**
	 * Exception thrown when the pensioner detail is not correct
	 */
	private static final long serialVersionUID = 1L;
	public ProcessPensionException(String message) {
		super(message);
	}
}

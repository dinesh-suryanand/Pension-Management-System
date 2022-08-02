package com.cognizant.process.pension.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MissingRequestHeaderException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.cognizant.process.pension.model.HttpResponse;

import java.io.IOException;

import javax.persistence.NoResultException;

@RestControllerAdvice
public class ExceptionHandling implements ErrorController {
	private final Logger logger = LoggerFactory.getLogger(getClass());
	private static final String INTERNAL_SERVER_ERROR_MSG = "An error occurred while processing the request";
	private static final String AUTHENTICATION_FAILED_MSG = "You are not authorized to make the request";
	private final String ERROR_PATH = "/error";

	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(MissingRequestHeaderException.class)
	public ResponseEntity<HttpResponse> missingAuthenticationHeaderException(MissingRequestHeaderException exception) {
		logger.error(exception.getMessage());
		return createHttpResponse(HttpStatus.BAD_REQUEST, AUTHENTICATION_FAILED_MSG);
	}

	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(NoResultException.class)
	public ResponseEntity<HttpResponse> notFoundException(NoResultException exception) {
		logger.error(exception.getMessage());
		return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
	}

	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(ProcessPensionException.class)
	public ResponseEntity<HttpResponse> pensionerDetailNotFoundException(ProcessPensionException exception) {
		logger.error(exception.getMessage());
		return createHttpResponse(HttpStatus.NOT_FOUND, exception.getMessage());
	}

	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(IOException.class)
	public ResponseEntity<HttpResponse> ioException(IOException exception) {
		logger.error(exception.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
	}

	/**
	 * @param exception
	 * @return
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<HttpResponse> internalServerErrorException(Exception exception) {
		logger.error(exception.getMessage());
		return createHttpResponse(HttpStatus.INTERNAL_SERVER_ERROR, INTERNAL_SERVER_ERROR_MSG);
	}

	/**
	 * @param httpStatus
	 * @param message
	 * @return
	 */
	private ResponseEntity<HttpResponse> createHttpResponse(HttpStatus httpStatus, String message) {
		HttpResponse httpResponse = new HttpResponse();
		httpResponse.setHttpStatusCode(httpStatus.value());
		httpResponse.setHttpStatus(httpStatus);
		httpResponse.setMessage(message);
		httpResponse.setReason(httpStatus.getReasonPhrase().toUpperCase());
		return new ResponseEntity<>(httpResponse, httpStatus);
	}

	@Override
	public String getErrorPath() {
		return ERROR_PATH;
	}
}
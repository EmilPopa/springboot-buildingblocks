package com.stacksimplify.restservices.exception;

import java.util.Date;
import java.util.Set;

import javax.validation.ConstraintViolationException;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.CollectionUtils;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // applicable to all the controllers - fara asta imi arunca o exceptie sablon
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

	// MethodArgumentNotValidException --suprascriu aceasta metoda care se afla in
	// // ResponseEntityExceptionHandler

	/**
	 * Customize the response for MethodArgumentNotValidException.
	 * <p>
	 * This method delegates to {@link #handleExceptionInternal}.
	 * 
	 * @param ex      the exception
	 * @param headers the headers to be written to the response
	 * @param status  the selected response status
	 * @param request the current request
	 * @return a {@code ResponseEntity} instance
	 */

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
				"From MethodArgumentNotValidException in GlobalExceptionHandler", ex.getMessage());
		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
	}

	// HttpRequestMethodNotSupportedException
	@Override
	protected ResponseEntity<Object> handleHttpRequestMethodNotSupported(HttpRequestMethodNotSupportedException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
//ex: incerc sa creez user cu patch 
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(),
				"From HttpRequestMethodNotSupportedException in GlobalExceptionHandler- Method not allowed",
				ex.getMessage());
		return new ResponseEntity<>(customErrorDetails, HttpStatus.METHOD_NOT_ALLOWED);
	}

	// UsernameNotFoundException will be handled
	@ExceptionHandler(UsernameNotFoundException.class)
	private final ResponseEntity<Object> handleUsernameNotFoundException(UsernameNotFoundException ex,
			WebRequest request) {
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(customErrorDetails, HttpStatus.NOT_FOUND);
	}

	// handler for//ConstraintViolationException
	@ExceptionHandler(ConstraintViolationException.class)
	public final ResponseEntity<Object> handleConstraintViolationException(ConstraintViolationException ex,
			WebRequest request) {
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(customErrorDetails, HttpStatus.BAD_REQUEST);
	}

	// UsernameNotFoundException will be handled -- este facuta de mine
	@ExceptionHandler(UserNotFoundException.class)
	private final ResponseEntity<Object> handleUserNotFoundException(UserNotFoundException ex, WebRequest request) {
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(customErrorDetails, HttpStatus.NOT_FOUND);
	}

	// OrderNotFoundException will be handled -- este facuta de mine
	@ExceptionHandler(OrderNotFoundException.class)
	private final ResponseEntity<Object> handleOrderNotFoundException(OrderNotFoundException ex, WebRequest request) {
		CustomErrorDetails customErrorDetails = new CustomErrorDetails(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<>(customErrorDetails, HttpStatus.NOT_FOUND);
	}

}

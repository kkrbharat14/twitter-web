package org.hackerone.twitterengine.handlers;

import javax.validation.ConstraintViolationException;

import org.hackerone.twitterengine.exceptions.APIError;
import org.hackerone.twitterengine.exceptions.InvalidMappingException;
import org.hackerone.twitterengine.exceptions.UnexpectedBackendException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;

@ControllerAdvice
public class RestAPIExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(value = UnexpectedBackendException.class)
	protected ResponseEntity<APIError> handleBackendBadResponseException(Exception ex, WebRequest request) {
		return new ResponseEntity<APIError>(
				new APIError(HttpStatus.SERVICE_UNAVAILABLE, "Unexpected failure from twitter api"),
				HttpStatus.SERVICE_UNAVAILABLE);
	}
	
	@ExceptionHandler(value = {JsonMappingException.class, JsonProcessingException.class})
	protected ResponseEntity<APIError> handleUnexpectedException(Exception ex, WebRequest request) {
		return new ResponseEntity<APIError>(
				new APIError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong while processing request"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = ConstraintViolationException.class)
	protected ResponseEntity<APIError> handleConstraintViolationException(Exception ex, WebRequest request) {
		return new ResponseEntity<APIError>(
				new APIError(HttpStatus.BAD_REQUEST, ex.getMessage()),
				HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(value = InvalidMappingException.class)
	protected ResponseEntity<APIError> handleGenericException(Exception ex, WebRequest request) {
		return new ResponseEntity<APIError>(
				new APIError(HttpStatus.INTERNAL_SERVER_ERROR, "Something went wrong during Mapping Results"),
				HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}

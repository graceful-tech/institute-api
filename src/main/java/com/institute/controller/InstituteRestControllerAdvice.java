package com.institute.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.institute.exception.InstituteException;

@RestControllerAdvice
public class InstituteRestControllerAdvice extends BaseController {

	private static final Logger logger = LoggerFactory.getLogger(InstituteRestControllerAdvice.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<?> handleValidationExceptions(MethodArgumentNotValidException ex) {
		return new ResponseEntity<>(buildResponse(ex), HttpStatus.BAD_REQUEST);
	}

	@ExceptionHandler(value = { InstituteException.class })
	public ResponseEntity<?> handleException(InstituteException exception) {
		logger.error("Controller :: handleException :: InstituteException :: " + exception.getCode());
		return new ResponseEntity<>(buildResponse(exception.getCode()), HttpStatus.INTERNAL_SERVER_ERROR);
	}
}

package com.curriculum.controller;

import org.apache.log4j.Logger;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.curriculum.util.Response;

public class ControllerAdvice {
	private Logger logger = Logger.getLogger(ControllerAdvice.class);

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> validationFailed(MethodArgumentNotValidException e) {
		logger.error("Validation fails, Check your input!");
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		response.setCode(422);
		response.setMessage("Validation fails!");
		responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
		return responseEntity;
	}
}

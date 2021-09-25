package com.curriculum.util;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ResponseUtil {
	public static ResponseEntity<Response> getResponse(Integer code, String message, Object data) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		if (code == 200) {
			response.setCode(code);
			response.setMessage(message);
			response.setData(data);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} else if (code == 404) {
			response.setCode(code);
			response.setMessage(message);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} else if (code == 422) {
			response.setCode(code);
			response.setMessage(message);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			response.setCode(500);
			response.setMessage(message);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	public static ResponseEntity<Response> getResponse(Integer code, String message) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		if (code == 404) {
			response.setCode(code);
			response.setMessage(message);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		} else if (code == 422) {
			response.setCode(code);
			response.setMessage(message);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
		} else {
			response.setCode(500);
			response.setMessage(message);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}

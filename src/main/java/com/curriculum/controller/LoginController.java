package com.curriculum.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.Login;
import com.curriculum.entity.LoginEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.LoginService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	private Logger logger = Logger.getLogger(LoginController.class);

	@PostMapping
	public ResponseEntity<Response> addLogin(@Valid @RequestBody Login login) {
		ResponseEntity<Response> responseEntity = null;
		Long loginId = null;
		try {
			loginId = loginService.addLogin(login);
			login.setLoginId(loginId);
			login.setTeacher(login.getTeacher());
			responseEntity = ResponseUtil.getResponse(200, "Login credentials added successfully!", login);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			if (e instanceof ConstraintValidationException) {
				responseEntity = ResponseUtil.getResponse(422, e.getMessage());
			} else {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
			}
		}
		return responseEntity;
	}

	@GetMapping("/{teacherId}")
	public ResponseEntity<Response> getLogin(@PathVariable("teacherId") Long teacherId) {
		ResponseEntity<Response> responseEntity = null;
		LoginEntity loginEntity = null;
		try {
			loginEntity = loginService.getLogin(teacherId);
			if (loginEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", loginEntity);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No User Found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@PutMapping("/{teacherId}")
	public ResponseEntity<Response> updateLogin(@PathVariable("teacherId") Long teacherId,
			@Valid @RequestBody Login login) {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		LoginEntity loginEntity = null;
		try {
			loginEntity = loginService.updateLogin(teacherId, login);
			response.setCode(200);
			response.setMessage("Password Changed Successfully!");
			response.setData(loginEntity);
			responseEntity = ResponseUtil.getResponse(200, "Password Changed Successfully!", loginEntity);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			if (e instanceof ConstraintValidationException) {
				responseEntity = ResponseUtil.getResponse(422, e.getMessage());
			} else {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
			}
		}
		return responseEntity;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> validationFailed(MethodArgumentNotValidException e) {
		logger.error("Validation fails, Check your input!");
		ResponseEntity<Response> responseEntity = null;
		responseEntity = ResponseUtil.getResponse(422, "Validation fails!");
		return responseEntity;
	}
}

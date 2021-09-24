package com.curriculum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.service.LoginService;
import com.curriculum.util.Response;

@RestController
@RequestMapping("api/login")
public class LoginController {
	@Autowired
	private LoginService loginService;
	@PostMapping
	public ResponseEntity<Response> addLogin(@Valid @RequestBody Login login)
	{
		ResponseEntity<Response> responseEntity=null;
		Response response=new Response();
		Long loginId=null;
		try {
			loginId=loginService.addLogin(login);
			response.setCode(200);
			response.setMessage("Login credentials added successfully!");
			login.setLoginId(loginId);
			login.setTeacher(login.getTeacher());
			response.setData(login);
			responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			if(e instanceof TeacherNotFoundException)
			{
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
			else if(e instanceof ConstraintValidationException)
			{
				response.setCode(422);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.UNPROCESSABLE_ENTITY);
			}
			else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}
	@GetMapping("/{teacherId}")
	public ResponseEntity<Response> getLogin(@PathVariable("teacherId") Long teacherId)
	{
		ResponseEntity<Response> responseEntity=null;
		Response response=new Response();
		LoginEntity loginEntity=null;
		try {
			loginEntity=loginService.getLogin(teacherId);
			if(loginEntity!=null)
			{
				response.setCode(200);
				response.setMessage("Success");
				response.setData(loginEntity);
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.OK);
			}else {
				response.setCode(404);
				response.setMessage("No User Found!");
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if(e instanceof TeacherNotFoundException)
			{
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
			else
			{
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}
	@PutMapping("/{teacherId}")
	public ResponseEntity<Response> updateLogin(@PathVariable("teacherId") Long teacherId,@Valid @RequestBody Login login)
	{
		ResponseEntity<Response> responseEntity=null;
		Response response=new Response();
		LoginEntity loginEntity=null;
		try {
			loginEntity=loginService.updateLogin(teacherId, login);
			response.setCode(200);
			response.setMessage("Password Changed Successfully!");
			response.setData(loginEntity);
			responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			if(e instanceof TeacherNotFoundException)
			{
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
			else
			{
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> validationFailed(MethodArgumentNotValidException e)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		response.setCode(422);
		response.setMessage("Validation fails!");
		responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.UNPROCESSABLE_ENTITY);
		return responseEntity;
	}
}

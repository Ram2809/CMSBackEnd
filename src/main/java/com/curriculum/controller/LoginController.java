//package com.curriculum.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.curriculum.entity.Login;
//import com.curriculum.exception.BusinessServiceException;
//import com.curriculum.service.LoginService;
//import com.curriculum.util.Response;
//
//@RestController
//@RequestMapping("api/login")
//public class LoginController {
//	@Autowired
//	private LoginService loginService;
//	@PostMapping
//	public ResponseEntity<Response> addLogin(@RequestBody Login login)
//	{
//		ResponseEntity<Response> responseEntity=null;
//		Response response=new Response();
//		Login loginDetails=null;
//		try {
//			loginDetails=loginService.addLogin(login);
//			response.setCode(200);
//			response.setMessage("Login credentials added successfully!");
//			response.setData(loginDetails);
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//	@GetMapping("/{teacherId}")
//	public ResponseEntity<Response> getLogin(@PathVariable("teacherId") Long teacherId)
//	{
//		ResponseEntity<Response> responseEntity=null;
//		Response response=new Response();
//		Login login=null;
//		try {
//			login=loginService.getLogin(teacherId);
//			response.setCode(200);
//			response.setMessage("Success");
//			response.setData(login);
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//		}
//		return responseEntity;
//	}
//	@PutMapping("/{teacherId}")
//	public ResponseEntity<Response> updateLogin(@PathVariable("teacherId") Long teacherId,@RequestBody Login login)
//	{
//		ResponseEntity<Response> responseEntity=null;
//		Response response=new Response();
//		Login loginDetails=null;
//		try {
//			loginDetails=loginService.updateLogin(teacherId, login);
//			response.setCode(200);
//			response.setMessage("Password Changed Successfully!");
//			response.setData(loginDetails);
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//}

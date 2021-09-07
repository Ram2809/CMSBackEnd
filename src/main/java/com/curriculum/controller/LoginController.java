package com.curriculum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.Login;
import com.curriculum.service.LoginService;

@RestController
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private LoginService loginServiceImpl;
	@PostMapping("/teacher/{teacherId}/addLogin")
	public ResponseEntity<String> addLoginDetails(@PathVariable("teacherId") Long teacherId,@RequestBody Login loginDetails)
	{
		return loginServiceImpl.addLogin(teacherId,loginDetails);
	}
	@GetMapping("/getLogin/{teacherId}")
	public ResponseEntity<Login> getLoginDetails(@PathVariable("teacherId") Long teacherId)
	{
		return loginServiceImpl.getLoginDetails(teacherId);
	}
	@PutMapping("/updateLogin/{teacherId}")
	public ResponseEntity<String> updateLoginDetails(@PathVariable("teacherId") Long teacherId,@RequestBody Login loginDetails)
	{
		return loginServiceImpl.updateLoginDetails(teacherId,loginDetails);
	}
}

package com.curriculum.service;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Login;

public interface LoginService {
	ResponseEntity<String> addLogin(Long teacherId,Login loginDetails);
	ResponseEntity<Login> getLoginDetails(Long teacherId);
	ResponseEntity<String> updateLoginDetails(Long teacherId,Login loginDetails);
}
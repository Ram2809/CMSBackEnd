package com.curriculum.service;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Login;
import com.curriculum.exception.BusinessServiceException;

public interface LoginService {
	Login addLogin(Login loginDetails) throws BusinessServiceException;
	Login getLogin(Long teacherId) throws BusinessServiceException;
	Login updateLogin(Long teacherId,Login loginDetails) throws BusinessServiceException;
}

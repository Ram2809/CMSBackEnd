package com.curriculum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.Login;
import com.curriculum.repository.LoginRepository;
import com.curriculum.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	private LoginRepository loginRepositoryImpl;
	@Override
	public ResponseEntity<String> addLogin(Long teacherId, Login loginDetails) {
		// TODO Auto-generated method stub
		return loginRepositoryImpl.addLogin(teacherId,loginDetails);
	}
	@Override
	public ResponseEntity<Login> getLoginDetails(Long teacherId) {
		// TODO Auto-generated method stub
		return loginRepositoryImpl.getLoginDetails(teacherId);
	}
	@Override
	public ResponseEntity<String> updateLoginDetails(Long teacherId, Login loginDetails) {
		// TODO Auto-generated method stub
		return loginRepositoryImpl.updateLoginDetails(teacherId,loginDetails);
	}

}

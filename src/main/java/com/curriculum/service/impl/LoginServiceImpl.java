//package com.curriculum.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.curriculum.entity.Login;
//import com.curriculum.exception.BusinessServiceException;
//import com.curriculum.exception.DatabaseException;
//import com.curriculum.repository.LoginRepository;
//import com.curriculum.service.LoginService;
//
//@Service
//public class LoginServiceImpl implements LoginService{
//	@Autowired
//	private LoginRepository loginRepository;
//	@Override
//	public Login addLogin(Login loginDetails) throws BusinessServiceException {
//		try {
//			return loginRepository.addLogin(loginDetails);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public Login getLogin(Long teacherId) throws BusinessServiceException {
//		try {
//			return loginRepository.getLogin(teacherId);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public Login updateLogin(Long teacherId, Login loginDetails) throws BusinessServiceException {
//		try {
//			return loginRepository.updateLogin(teacherId,loginDetails);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//}

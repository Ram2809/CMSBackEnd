package com.curriculum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Login;
import com.curriculum.entity.LoginEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.repository.LoginRepository;
import com.curriculum.repository.impl.TeacherRepositoryImpl;
import com.curriculum.service.LoginService;

@Service
public class LoginServiceImpl implements LoginService{
	@Autowired
	private LoginRepository loginRepository;
	@Autowired
	private TeacherRepositoryImpl teacherRepository;
	@Override
	public Long addLogin(Login login) throws BusinessServiceException, NotFoundException {
		try {
			teacherRepository.checkTeacher(login.getTeacher().getId());
			return loginRepository.addLogin(login);
		}catch (DataIntegrityViolationException e) {
			throw new ConstraintValidationException("Constraint Violation fails!");
		}
		catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public LoginEntity getLogin(Long teacherId) throws BusinessServiceException, NotFoundException {
		try {
			teacherRepository.checkTeacher(teacherId);
			return loginRepository.getLogin(teacherId);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public LoginEntity updateLogin(Long teacherId, Login login) throws BusinessServiceException, NotFoundException {
		try {
			teacherRepository.checkTeacher(teacherId);
			return loginRepository.updateLogin(teacherId,login);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
}

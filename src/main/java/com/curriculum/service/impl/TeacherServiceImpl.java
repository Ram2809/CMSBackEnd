package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Teacher;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.TeacherRepository;
import com.curriculum.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {
	@Autowired
	private TeacherRepository teacherRepository;

	@Override
	public Long addTeacher(Teacher teacher) throws BusinessServiceException {
		try {
			return teacherRepository.addTeacher(teacher);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<TeacherEntity> getAllTeacher() throws BusinessServiceException {
		try {
			return teacherRepository.getAllTeacher();
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public TeacherEntity updateTeacher(Long id, Teacher teacherDetails)
			throws BusinessServiceException, NotFoundException {
		try {
			return teacherRepository.updateTeacher(id, teacherDetails);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public TeacherEntity deleteTeacher(Long id) throws BusinessServiceException, NotFoundException {
		try {
			return teacherRepository.deleteTeacher(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public TeacherEntity getParticularTeacher(Long id) throws BusinessServiceException, NotFoundException {
		try {
			return teacherRepository.getParticularTeacher(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

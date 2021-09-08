package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.Teacher;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.repository.TeacherRepository;
import com.curriculum.service.TeacherService;

@Service
public class TeacherServiceImpl implements TeacherService {
	@Autowired
	private TeacherRepository teacherRepositoryImpl;

	@Override
	public ResponseEntity<String> addTeacherDetails(Teacher teacherDetails) {
		// TODO Auto-generated method stub
		return teacherRepositoryImpl.addTeacherDetails(teacherDetails);
	}

	@Override
	public ResponseEntity<List<Teacher>> getAllTeacherDetails() {
		// TODO Auto-generated method stub
		return teacherRepositoryImpl.getAllTeacherDetails();
	}

	@Override
	public ResponseEntity<String> updateTeacherDetails(Long id, Teacher teacherDetails)
			throws BusinessServiceException {
		// TODO Auto-generated method stub
		try {
			return teacherRepositoryImpl.updateTeacherDetails(id, teacherDetails);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<String> deleteTeacherDetails(Long id) throws BusinessServiceException {
		// TODO Auto-generated method stub
		try {
			return teacherRepositoryImpl.deleteTeacherDetails(id);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public ResponseEntity<Teacher> getParticularTeacherDetails(Long id) throws BusinessServiceException {
		// TODO Auto-generated method stub
		try {
			return teacherRepositoryImpl.getParticularTeacherDetails(id);
		} catch (DatabaseException e) {
			// TODO Auto-generated catch block
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

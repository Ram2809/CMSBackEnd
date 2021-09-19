package com.curriculum.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.Student;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.StudentNotFoundException;
import com.curriculum.repository.StudentRepository;
import com.curriculum.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepository studentRepositoryImpl;
	@Override
	public Student addStudent(Student studentDetails) throws BusinessServiceException {
		try {
			return studentRepositoryImpl.addStudent(studentDetails);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public ResponseEntity<List<Student>> getAllStudentDetails() {
		// TODO Auto-generated method stub
		return studentRepositoryImpl.getAllStudentDetails();
	}
	@Override
	public ResponseEntity<String> updateStudentDetails(Long rollNo, Student studentDetails) throws StudentNotFoundException {
		// TODO Auto-generated method stub
		return studentRepositoryImpl.updateStudentDetails(rollNo,studentDetails);
	}
	@Override
	public Student deleteStudent(Long rollNo) throws BusinessServiceException{
		try {
			return studentRepositoryImpl.deleteStudent(rollNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public ResponseEntity<Student> getParticularStudentDetails(Long rollNo) throws StudentNotFoundException {
		// TODO Auto-generated method stub
		return studentRepositoryImpl.getParticularStudentDetails(rollNo);
	}
	@Override
	public List<Student> getStudentByClass(Long roomNo) throws BusinessServiceException {
		try {
			return studentRepositoryImpl.getStudentByClass(roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

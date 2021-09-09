package com.curriculum.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.Student;
import com.curriculum.exception.StudentNotFoundException;
import com.curriculum.repository.StudentRepository;
import com.curriculum.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepository studentRepositoryImpl;
	@Override
	public ResponseEntity<String> addStudentDetails(Long roomNo,Student studentDetails) {
		// TODO Auto-generated method stub
		return studentRepositoryImpl.addStudentDetails(roomNo,studentDetails);
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
	public ResponseEntity<String> deleteStudentDetails(Long rollNo) throws StudentNotFoundException {
		// TODO Auto-generated method stub
		return studentRepositoryImpl.deleteStudentDetails(rollNo);
	}
	@Override
	public ResponseEntity<Student> getParticularStudentDetails(Long rollNo) throws StudentNotFoundException {
		// TODO Auto-generated method stub
		return studentRepositoryImpl.getParticularStudentDetails(rollNo);
	}
	@Override
	public ResponseEntity<List<Student>> getStudentByClass(Long roomNo) {
		// TODO Auto-generated method stub
		return studentRepositoryImpl.getStudentByClass(roomNo);
	}

}

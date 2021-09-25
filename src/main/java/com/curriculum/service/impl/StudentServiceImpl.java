package com.curriculum.service.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Student;
import com.curriculum.entity.StudentEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.ClassRepository;
import com.curriculum.repository.StudentRepository;
import com.curriculum.service.StudentService;

@Service
@Transactional
public class StudentServiceImpl implements StudentService{
	
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ClassRepository classRepository;
	private Logger logger=Logger.getLogger(StudentServiceImpl.class);
	@Override
	public Long addStudent(Student student) throws BusinessServiceException, NotFoundException {
		try {
			classRepository.checkClassRoom(student.getClassDetail().getRoomNo());
			return studentRepository.addStudent(student);
		} catch (DataIntegrityViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
//	@Override
//	public ResponseEntity<List<Student>> getAllStudentDetails() {
//		// TODO Auto-generated method stub
//		return studentRepository.getAllStudentDetails();
//	}
//	@Override
//	public ResponseEntity<String> updateStudentDetails(Long rollNo, Student studentDetails) throws StudentNotFoundException {
//		// TODO Auto-generated method stub
//		return studentRepository.updateStudentDetails(rollNo,studentDetails);
//	}
	@Override
	public StudentEntity deleteStudent(Long rollNo) throws BusinessServiceException, NotFoundException{
		try {
			return studentRepository.deleteStudent(rollNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
//	@Override
//	public ResponseEntity<Student> getParticularStudentDetails(Long rollNo) throws StudentNotFoundException {
//		// TODO Auto-generated method stub
//		return studentRepository.getParticularStudentDetails(rollNo);
//	}
	@Override
	public List<StudentEntity> getStudentByClass(Long roomNo) throws BusinessServiceException, NotFoundException {
		try {
			classRepository.checkClassRoom(roomNo);
			return studentRepository.getStudentByClass(roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

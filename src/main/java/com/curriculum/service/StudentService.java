package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Student;
import com.curriculum.entity.StudentEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface StudentService {
	Long addStudent(Student student) throws BusinessServiceException, NotFoundException;

//	ResponseEntity<List<Student>> getAllStudentDetails();
//
//	ResponseEntity<String> updateStudentDetails(Long rollNo, Student studentDetails) throws StudentNotFoundException;
//
	StudentEntity deleteStudent(Long rollNo) throws BusinessServiceException, NotFoundException;
//
//	ResponseEntity<Student> getParticularStudentDetails(Long rollNo) throws StudentNotFoundException;

	List<StudentEntity> getStudentByClass(Long roomNo) throws BusinessServiceException,NotFoundException;
}

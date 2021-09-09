package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Student;
import com.curriculum.exception.StudentNotFoundException;

public interface StudentService {
	ResponseEntity<String> addStudentDetails(Long roomNo, Student studentDetails);

	ResponseEntity<List<Student>> getAllStudentDetails();
	ResponseEntity<String> updateStudentDetails(Long rollNo,Student studentDetails) throws StudentNotFoundException;
	ResponseEntity<String> deleteStudentDetails(Long rollNo) throws StudentNotFoundException; 
	ResponseEntity<Student> getParticularStudentDetails(Long rollNo) throws StudentNotFoundException;
	ResponseEntity<List<Student>> getStudentByClass(Long roomNo);
}

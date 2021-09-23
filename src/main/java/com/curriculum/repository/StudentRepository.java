package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.dto.Student;
import com.curriculum.entity.StudentEntity;
import com.curriculum.exception.ClassNotFoundException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.StudentNotFoundException;

public interface StudentRepository {
	Long addStudent(Student student) throws DatabaseException;
//
//	ResponseEntity<List<Student>> getAllStudentDetails();
//
//	ResponseEntity<String> updateStudentDetails(Long rollNo, Student studentDetails) throws StudentNotFoundException;
//
	StudentEntity deleteStudent(Long rollNo) throws DatabaseException,NotFoundException;

//	ResponseEntity<Student> getParticularStudentDetails(Long rollNo) throws StudentNotFoundException;

	List<StudentEntity> getStudentByClass(Long roomNo) throws DatabaseException;
}

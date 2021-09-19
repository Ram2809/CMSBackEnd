package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Student;
import com.curriculum.exception.ClassNotFoundException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.StudentNotFoundException;

public interface StudentRepository {
	Student addStudent(Student studentDetails) throws DatabaseException;

	ResponseEntity<List<Student>> getAllStudentDetails();

	ResponseEntity<String> updateStudentDetails(Long rollNo, Student studentDetails) throws StudentNotFoundException;

	Student deleteStudent(Long rollNo) throws DatabaseException;

	ResponseEntity<Student> getParticularStudentDetails(Long rollNo) throws StudentNotFoundException;

	List<Student> getStudentByClass(Long roomNo) throws DatabaseException;
}

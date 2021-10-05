package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Student;
import com.curriculum.entity.StudentEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface StudentRepository {
	Long addStudent(Student student) throws DatabaseException;

	StudentEntity updateStudent(Long rollNo, Student student) throws DatabaseException;

	StudentEntity deleteStudent(Long rollNo) throws DatabaseException, NotFoundException;

	StudentEntity getStudent(Long rollNo) throws NotFoundException, DatabaseException;

	List<StudentEntity> getStudentByClass(Long roomNo) throws DatabaseException;
}

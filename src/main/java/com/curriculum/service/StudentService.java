package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Student;
import com.curriculum.entity.StudentEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface StudentService {
	Long addStudent(Student student) throws BusinessServiceException, NotFoundException;

	StudentEntity updateStudent(Long rollNo, Student student) throws NotFoundException, BusinessServiceException;

	StudentEntity deleteStudent(Long rollNo) throws BusinessServiceException, NotFoundException;

	StudentEntity getStudent(Long rollNo) throws BusinessServiceException, NotFoundException;

	List<StudentEntity> getStudentByClass(Long roomNo) throws BusinessServiceException, NotFoundException;
}

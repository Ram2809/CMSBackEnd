package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Teacher;
import com.curriculum.exception.BusinessServiceException;

public interface TeacherService {
	ResponseEntity<String> addTeacherDetails(Teacher teacherDetails);

	ResponseEntity<List<Teacher>> getAllTeacherDetails();

	ResponseEntity<String> updateTeacherDetails(Long id, Teacher teacherDetails) throws BusinessServiceException;

	ResponseEntity<String> deleteTeacherDetails(Long id) throws BusinessServiceException;

	ResponseEntity<Teacher> getParticularTeacherDetails(Long id) throws BusinessServiceException;
}

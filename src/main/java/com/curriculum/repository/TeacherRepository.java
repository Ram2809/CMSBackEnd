package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Teacher;
import com.curriculum.exception.DatabaseException;

public interface TeacherRepository {
	ResponseEntity<?> addTeacherDetails(Teacher teacherDeteails);

	ResponseEntity<List<Teacher>> getAllTeacherDetails();

	ResponseEntity<String> updateTeacherDetails(Long id, Teacher teacherDetails) throws DatabaseException;

	ResponseEntity<String> deleteTeacherDetails(Long id) throws DatabaseException;

	ResponseEntity<Teacher> getParticularTeacherDetails(Long id) throws DatabaseException;
}

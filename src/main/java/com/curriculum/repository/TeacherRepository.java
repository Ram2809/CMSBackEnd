package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Teacher;
import com.curriculum.exception.DatabaseException;

public interface TeacherRepository {
	Teacher addTeacher(Teacher teacher) throws DatabaseException;

	List<Teacher> getAllTeacher() throws DatabaseException;

	Teacher updateTeacher(Long id, Teacher teacherDetails) throws DatabaseException;

	Teacher deleteTeacher(Long id) throws DatabaseException;

	Teacher getParticularTeacher(Long id) throws DatabaseException;
}

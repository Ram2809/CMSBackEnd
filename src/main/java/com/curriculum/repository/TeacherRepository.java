package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Teacher;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface TeacherRepository {
	Long addTeacher(Teacher teacher) throws DatabaseException;

	List<TeacherEntity> getAllTeacher() throws DatabaseException;

	TeacherEntity updateTeacher(Long id, Teacher teacherDetails) throws DatabaseException, NotFoundException;

	TeacherEntity deleteTeacher(Long id) throws DatabaseException, NotFoundException;

	TeacherEntity getParticularTeacher(Long id) throws DatabaseException, NotFoundException;

	void checkTeacher(Long id) throws NotFoundException;
}

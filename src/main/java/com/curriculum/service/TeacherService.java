package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Teacher;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TeacherService {
	Long addTeacher(Teacher teacher) throws BusinessServiceException;

	List<TeacherEntity> getAllTeacher() throws BusinessServiceException;

	TeacherEntity updateTeacher(Long id, Teacher teacherDetails) throws BusinessServiceException, NotFoundException;

	TeacherEntity deleteTeacher(Long id) throws BusinessServiceException, NotFoundException;

	TeacherEntity getParticularTeacher(Long id) throws BusinessServiceException, NotFoundException;
}

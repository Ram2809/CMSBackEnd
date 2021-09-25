package com.curriculum.service;

import org.springframework.http.ResponseEntity;

import com.curriculum.dto.TeacherSubject;
import com.curriculum.entity.TeacherSubjectEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TeacherSubjectService {
	Long assignTeacherSubject(TeacherSubject teacherSubject) throws BusinessServiceException, NotFoundException;

	TeacherSubjectEntity updateTeacherSubjectAssign(Long id, TeacherSubject teacherSubject)
			throws BusinessServiceException, NotFoundException;

	TeacherSubjectEntity deleteTeacherSubjectAssign(Long id) throws BusinessServiceException, NotFoundException;
}

package com.curriculum.repository;

import com.curriculum.dto.TeacherSubject;
import com.curriculum.entity.TeacherSubjectEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface TeacherSubjectRepository {
	Long assignTeacherSubject(TeacherSubject teacherSubject) throws DatabaseException;

	TeacherSubjectEntity updateTeacherSubjectAssign(Long id, TeacherSubject teacherSubject)
			throws DatabaseException, NotFoundException;

	TeacherSubjectEntity deleteTeacherSubjectAssign(Long assignId) throws DatabaseException, NotFoundException;
}

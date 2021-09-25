package com.curriculum.repository;

import com.curriculum.dto.TeacherAssign;
import com.curriculum.entity.TeacherAssignEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface TeacherAssignRepository {
	Long assignTeacherSubject(TeacherAssign teacherAssign) throws DatabaseException;

	TeacherAssignEntity updateTeacherSubjectAssign(Long id, TeacherAssign teacherAssign)
			throws DatabaseException, NotFoundException;

	TeacherAssignEntity deleteTeacherSubjectAssign(Long assignId) throws DatabaseException, NotFoundException;
}

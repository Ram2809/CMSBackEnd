package com.curriculum.service;

import com.curriculum.dto.TeacherAssign;
import com.curriculum.entity.TeacherAssignEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TeacherAssignService {
	Long assignTeacherSubject(TeacherAssign teacherAssign) throws BusinessServiceException, NotFoundException;

	TeacherAssignEntity updateTeacherSubjectAssign(Long id, TeacherAssign teacherAssign)
			throws BusinessServiceException, NotFoundException;

	TeacherAssignEntity deleteTeacherSubjectAssign(Long id) throws BusinessServiceException, NotFoundException;
}

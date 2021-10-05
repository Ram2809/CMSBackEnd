package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.TeacherAssign;
import com.curriculum.entity.TeacherAssignEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TeacherAssignService {
	Long assignTeacherSubject(TeacherAssign teacherAssign) throws BusinessServiceException, NotFoundException;

	TeacherAssignEntity deleteTeacherSubjectAssign(Long id) throws BusinessServiceException, NotFoundException;

	List<Long> getSubjectAssignIds(Long staffId) throws BusinessServiceException, NotFoundException;

	Long getTeacherId(Long id) throws BusinessServiceException, NotFoundException;

	Long updateTeacherAssign(Long assignId, Long staffId) throws BusinessServiceException, NotFoundException;
}

package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.StudentAssignDTO;
import com.curriculum.entity.StudentAssign;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface StudentAssignService {
	Long addStudentAssign(StudentAssignDTO studentAssignDTO) throws BusinessServiceException, NotFoundException;

	List<StudentAssign> getStudentClassDetails(Long roomNo, String academicYear)
			throws BusinessServiceException, NotFoundException;

	StudentAssign updateStudentAssign(Long assignId, StudentAssignDTO studentAssignDTO) throws BusinessServiceException, NotFoundException;
}

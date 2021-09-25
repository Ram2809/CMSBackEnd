package com.curriculum.service;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface SubjectAssignService {
	Long addSubjectAssign(SubjectAssign subjectAssign) throws BusinessServiceException, NotFoundException;
}

package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface SubjectAssignService {
	Long addSubjectAssign(SubjectAssign subjectAssign) throws BusinessServiceException, NotFoundException;

	List<SubjectAssignEntity> getSubjects(Long roomNo) throws NotFoundException, BusinessServiceException;

	Long getAssignId(Long roomNo, String subjectCode) throws BusinessServiceException, NotFoundException;

	String getSubjectCode(Long id, Long roomNo) throws BusinessServiceException, NotFoundException;

	Long getRoomNo(Long id) throws NotFoundException, BusinessServiceException;
}
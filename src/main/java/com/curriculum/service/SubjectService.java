package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Subject;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface SubjectService {
	String addSubject(Subject subject) throws BusinessServiceException, NotFoundException;

	SubjectEntity updateSubject(String subjectCode, Subject subject) throws BusinessServiceException, NotFoundException;

	SubjectEntity deleteSubject(String subjectCode) throws BusinessServiceException, NotFoundException;

	SubjectEntity getParticularSubject(String subjectCode) throws BusinessServiceException, NotFoundException;

	List<SubjectEntity> getSubjectList(List<String> subjectCodeList) throws BusinessServiceException, NotFoundException;
}

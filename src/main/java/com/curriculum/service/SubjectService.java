package com.curriculum.service;


import java.util.List;

import com.curriculum.dto.Subject;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;


public interface SubjectService {
	String addSubject(Subject subject) throws BusinessServiceException, NotFoundException;

	//ResponseEntity<List<Subject>> getAllSubjectDetails();

	SubjectEntity updateSubject(String subjectCode, Subject subject) throws BusinessServiceException, NotFoundException;

	SubjectEntity deleteSubject(String subjectCode) throws BusinessServiceException, NotFoundException;

	SubjectEntity getParticularSubject(String subjectCode) throws BusinessServiceException, NotFoundException;

	List<SubjectEntity> getSubjectByClass(Long roomNo) throws BusinessServiceException, NotFoundException;

	List<String> getSubjectName(Long roomNo) throws BusinessServiceException, NotFoundException;

	String getSubjectCode(Long roomNo, String name) throws BusinessServiceException, NotFoundException;
}


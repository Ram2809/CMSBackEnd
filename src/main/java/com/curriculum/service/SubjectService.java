package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.dto.Subject;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.SubjectNotFoundException;

public interface SubjectService {
	String addSubject(Subject subject) throws BusinessServiceException, NotFoundException;

//	ResponseEntity<List<Subject>> getAllSubjectDetails();
//
//	Subject updateSubject(String subjectCode,Subject subjectDetails)throws BusinessServiceException;
//
//	Subject deleteSubject(String subjectCode) throws BusinessServiceException;
//
	SubjectEntity getParticularSubject(String subjectCode) throws BusinessServiceException, NotFoundException;
//
//	List<Subject> getSubjectByClass(Long roomNo) throws BusinessServiceException;
//
//	List<String> getSubjectName(Long roomNo) throws BusinessServiceException;
//
//	String getSubjectCode(Long roomNo, String name) throws BusinessServiceException;
}

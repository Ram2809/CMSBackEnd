package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Subject;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.SubjectNotFoundException;

public interface SubjectService {
	Subject addSubject(Subject subjectDetails) throws BusinessServiceException;

	ResponseEntity<List<Subject>> getAllSubjectDetails();

	Subject updateSubject(String subjectCode,Subject subjectDetails)throws BusinessServiceException;

	Subject deleteSubject(String subjectCode) throws BusinessServiceException;

	Subject getParticularSubject(String subjectCode) throws BusinessServiceException;

	List<Subject> getSubjectByClass(Long roomNo) throws BusinessServiceException;

	List<String> getSubjectName(Long roomNo) throws BusinessServiceException;

	String getSubjectCode(Long roomNo, String name) throws BusinessServiceException;
}

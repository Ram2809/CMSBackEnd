package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Subject;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.SubjectNotFoundException;

public interface SubjectService {
	Subject addSubject(Subject subjectDetails) throws BusinessServiceException;

	ResponseEntity<List<Subject>> getAllSubjectDetails();

	ResponseEntity<String> updateSubjectDetails(String subjectCode, Long roomNo, Subject subjectDetails)
			throws SubjectNotFoundException, ClassNotFoundException;

	ResponseEntity<String> deleteSubjectDetails(String subjectCode) throws SubjectNotFoundException;

	Subject getParticularSubject(String subjectCode) throws BusinessServiceException;

	ResponseEntity<List<Subject>> getSubjectByClass(Long roomNo) throws ClassNotFoundException;

	ResponseEntity<List<String>> getSubjectName(Long roomNo) throws ClassNotFoundException;

	ResponseEntity<String> getSubjectCode(Long roomNo, String name) throws ClassNotFoundException;
}

package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Subject;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.SubjectNotFoundException;

public interface SubjectRepository {
	Subject addSubject(Subject subjectDetails) throws DatabaseException;

	ResponseEntity<List<Subject>> getAllSubjectDetails();

	ResponseEntity<String> updateSubjectDetails(String subjectCode, Long roomNo, Subject subjectDetails)
			throws SubjectNotFoundException, ClassNotFoundException;

	ResponseEntity<String> deleteSubjectDetails(String subjectCode) throws SubjectNotFoundException;

	Subject getParticularSubject(String subjectCode) throws DatabaseException;

	ResponseEntity<List<Subject>> getSubjectByClass(Long roomNo) throws ClassNotFoundException;

	ResponseEntity<List<String>> getSubjectName(Long roomNo) throws ClassNotFoundException;

	ResponseEntity<String> getSubjectCode(Long roomNo, String name) throws ClassNotFoundException;
}

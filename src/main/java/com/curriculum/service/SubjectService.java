package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Subject;
import com.curriculum.exception.SubjectNotFoundException;

public interface SubjectService {
	ResponseEntity<String> addSubjectDetails(Long roomNo,Subject subjectDetails) throws ClassNotFoundException;
	ResponseEntity<List<Subject>> getAllSubjectDetails();
	ResponseEntity<String> updateSubjectDetails(String subjectCode,Long roomNo,Subject subjectDetails) throws SubjectNotFoundException, ClassNotFoundException;
	ResponseEntity<String> deleteSubjectDetails(String subjectCode) throws SubjectNotFoundException;
	ResponseEntity<Subject> getParticularSubjectDetails(String subjectCode) throws SubjectNotFoundException;
	ResponseEntity<List<Subject>> getSubjectByClass(Long roomNo) throws ClassNotFoundException;
}

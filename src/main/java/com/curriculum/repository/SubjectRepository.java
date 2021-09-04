package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Subject;
import com.curriculum.exception.SubjectNotFoundException;

public interface SubjectRepository {
	ResponseEntity<String> addSubjectDetails(Long roomNo,Subject subjectDetails) throws ClassNotFoundException;
	ResponseEntity<List<Subject>> getAllSubjectDetails();
	ResponseEntity<String> updateSubjectDetails(String subjectCode,Long roomNo,Subject subjectDetails) throws SubjectNotFoundException, ClassNotFoundException;
	ResponseEntity<String> deleteSubjectDetails(String subjectCode) throws SubjectNotFoundException;
	ResponseEntity<Subject> getParticularSubjectDetails(String subjectCode) throws SubjectNotFoundException;
}

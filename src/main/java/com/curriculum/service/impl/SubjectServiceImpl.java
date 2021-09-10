package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.Subject;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{
	@Autowired
	private SubjectRepository subjectRepositoryImpl;
	@Override
	public ResponseEntity<String> addSubjectDetails(Long roomNo, Subject subjectDetails) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return subjectRepositoryImpl.addSubjectDetails(roomNo,subjectDetails);
	}
	@Override
	public ResponseEntity<List<Subject>> getAllSubjectDetails() {
		// TODO Auto-generated method stub
		return subjectRepositoryImpl.getAllSubjectDetails();
	}
	@Override
	public ResponseEntity<String> updateSubjectDetails(String subjectCode, Long roomNo,Subject subjectDetails) throws SubjectNotFoundException, ClassNotFoundException {
		// TODO Auto-generated method stub
		return subjectRepositoryImpl.updateSubjectDetails(subjectCode,roomNo,subjectDetails);
	}
	@Override
	public ResponseEntity<String> deleteSubjectDetails(String subjectCode) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		return subjectRepositoryImpl.deleteSubjectDetails(subjectCode);
	}
	@Override
	public ResponseEntity<Subject> getParticularSubjectDetails(String subjectCode) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		return subjectRepositoryImpl.getParticularSubjectDetails(subjectCode);
	}
	@Override
	public ResponseEntity<List<Subject>> getSubjectByClass(Long roomNo) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return subjectRepositoryImpl.getSubjectByClass(roomNo);
	}
}

package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.Subject;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{
	@Autowired
	private SubjectRepository subjectRepositoryImpl;
	@Override
	public Subject addSubject(Subject subjectDetails) throws BusinessServiceException {
		try {
			return subjectRepositoryImpl.addSubject(subjectDetails);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
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
	public Subject getParticularSubject(String subjectCode) throws BusinessServiceException {
		try {
			return subjectRepositoryImpl.getParticularSubject(subjectCode);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public ResponseEntity<List<Subject>> getSubjectByClass(Long roomNo) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return subjectRepositoryImpl.getSubjectByClass(roomNo);
	}
	@Override
	public ResponseEntity<List<String>> getSubjectName(Long roomNo) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return subjectRepositoryImpl.getSubjectName(roomNo);
	}
	@Override
	public ResponseEntity<String> getSubjectCode(Long roomNo, String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return subjectRepositoryImpl.getSubjectCode(roomNo,name);
	}
}

package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.Subject;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
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
		return subjectRepositoryImpl.getAllSubjectDetails();
	}
	@Override
	public Subject updateSubject(String subjectCode,Subject subjectDetails) throws BusinessServiceException {
		try {
			return subjectRepositoryImpl.updateSubject(subjectCode,subjectDetails);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public Subject deleteSubject(String subjectCode) throws BusinessServiceException {
		try {
			return subjectRepositoryImpl.deleteSubject(subjectCode);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
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
	public List<Subject> getSubjectByClass(Long roomNo) throws BusinessServiceException {
		try {
			return subjectRepositoryImpl.getSubjectByClass(roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public List<String> getSubjectName(Long roomNo) throws BusinessServiceException {
		try {
			return subjectRepositoryImpl.getSubjectName(roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public String getSubjectCode(Long roomNo, String name) throws BusinessServiceException {
		try {
			return subjectRepositoryImpl.getSubjectCode(roomNo,name);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	
}

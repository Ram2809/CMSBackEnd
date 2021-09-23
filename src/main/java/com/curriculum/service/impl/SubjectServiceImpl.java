package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Subject;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ClassNotFoundException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.repository.impl.ClassRepositoryImpl;
import com.curriculum.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService{
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private ClassRepositoryImpl classRepository;
	@Override
	public String addSubject(Subject subject) throws BusinessServiceException, NotFoundException {
		try {
			classRepository.checkClassRoom(subject.getClassRoom().getRoomNo());
			return subjectRepository.addSubject(subject);
		}catch(DataIntegrityViolationException e)
		{
			throw new ConstraintValidationException("Constraint Violation fails!");
		}
		catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
//	@Override
//	public ResponseEntity<List<Subject>> getAllSubjectDetails() {
//		return subjectRepository.getAllSubjectDetails();
//	}
//	@Override
//	public Subject updateSubject(String subjectCode,Subject subjectDetails) throws BusinessServiceException {
//		try {
//			return subjectRepository.updateSubject(subjectCode,subjectDetails);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public Subject deleteSubject(String subjectCode) throws BusinessServiceException {
//		try {
//			return subjectRepository.deleteSubject(subjectCode);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
	@Override
	public SubjectEntity getParticularSubject(String subjectCode) throws BusinessServiceException, NotFoundException {
		try {
			return subjectRepository.getParticularSubject(subjectCode);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
//	@Override
//	public List<Subject> getSubjectByClass(Long roomNo) throws BusinessServiceException {
//		try {
//			return subjectRepository.getSubjectByClass(roomNo);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public List<String> getSubjectName(Long roomNo) throws BusinessServiceException {
//		try {
//			return subjectRepository.getSubjectName(roomNo);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public String getSubjectCode(Long roomNo, String name) throws BusinessServiceException {
//		try {
//			return subjectRepository.getSubjectCode(roomNo,name);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
	
}

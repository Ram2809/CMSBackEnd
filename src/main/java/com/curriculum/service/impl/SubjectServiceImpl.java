package com.curriculum.service.impl;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Subject;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.ClassRepository;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.service.SubjectService;

@Service
public class SubjectServiceImpl implements SubjectService {
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private ClassRepository classRepository;
	private Logger logger=Logger.getLogger(SubjectServiceImpl.class);
	@Override
	public String addSubject(Subject subject) throws BusinessServiceException, NotFoundException {
		try {
			classRepository.checkClassRoom(subject.getClassRoom().getRoomNo());
			return subjectRepository.addSubject(subject);
		} catch (DataIntegrityViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

//	@Override
//	public ResponseEntity<List<Subject>> getAllSubjectDetails() {
//		return subjectRepository.getAllSubjectDetails();
//	}
	@Override
	public SubjectEntity updateSubject(String subjectCode, Subject subject)
			throws BusinessServiceException, NotFoundException {
		try {
			classRepository.checkClassRoom(subject.getClassRoom().getRoomNo());
			return subjectRepository.updateSubject(subjectCode, subject);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}catch (DataIntegrityViolationException | ConstraintViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		}
	}

	@Override
	public SubjectEntity deleteSubject(String subjectCode) throws BusinessServiceException, NotFoundException {
		try {
			return subjectRepository.deleteSubject(subjectCode);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public SubjectEntity getParticularSubject(String subjectCode) throws BusinessServiceException, NotFoundException {
		try {
			return subjectRepository.getParticularSubject(subjectCode);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<SubjectEntity> getSubjectByClass(Long roomNo) throws BusinessServiceException, NotFoundException {
		try {
			classRepository.checkClassRoom(roomNo);
			return subjectRepository.getSubjectByClass(roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<String> getSubjectName(Long roomNo) throws BusinessServiceException, NotFoundException {
		try {
			classRepository.checkClassRoom(roomNo);
			return subjectRepository.getSubjectName(roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public String getSubjectCode(Long roomNo, String name) throws BusinessServiceException, NotFoundException {
		try {
			classRepository.checkClassRoom(roomNo);
			return subjectRepository.getSubjectCode(roomNo, name);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

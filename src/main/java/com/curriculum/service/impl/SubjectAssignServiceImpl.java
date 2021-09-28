package com.curriculum.service.impl;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.ClassRepository;
import com.curriculum.repository.SubjectAssignRepository;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.service.SubjectAssignService;

@Service
public class SubjectAssignServiceImpl implements SubjectAssignService {
	@Autowired
	private SubjectAssignRepository subjectAssignRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired
	private ClassRepository classRepository;

	@Override
	public Long addSubjectAssign(SubjectAssign subjectAssign) throws BusinessServiceException, NotFoundException {
		try {
			subjectRepository.checkSubject(subjectAssign.getSubject().getCode());
			classRepository.checkClassRoom(subjectAssign.getClassDetail().getRoomNo());
			return subjectAssignRepository.addSubjectAssign(subjectAssign);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			// logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		}
	}

	@Override
	public List<SubjectAssignEntity> getSubjects(Long roomNo) throws NotFoundException, BusinessServiceException {
		classRepository.checkClassRoom(roomNo);
		try {
			return subjectAssignRepository.getSubjects(roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public Long getAssignId(Long roomNo, String subjectCode) throws BusinessServiceException, NotFoundException {
		subjectRepository.checkSubject(subjectCode);
		classRepository.checkClassRoom(roomNo);
		try {
			return subjectAssignRepository.getAssignId(roomNo, subjectCode);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public String getSubjectCode(Long id) throws BusinessServiceException, NotFoundException {
		try {
			return subjectAssignRepository.getSubjectCode(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

package com.curriculum.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Unit;
import com.curriculum.entity.UnitEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.repository.UnitRepository;
import com.curriculum.service.UnitService;

@Service
public class UnitServiceImpl implements UnitService {
	@Autowired
	private UnitRepository topicRepositoryImpl;
	@Autowired
	private SubjectRepository subjectRepository;
	private Logger logger = Logger.getLogger(UnitServiceImpl.class);

	@Override
	public String addUnit(Unit unit) throws BusinessServiceException, NotFoundException {
		try {
			subjectRepository.checkSubject(unit.getSubject().getCode());
			return topicRepositoryImpl.addUnit(unit);
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<UnitEntity> getUnitBySubjectCode(String subjectCode)
			throws BusinessServiceException, NotFoundException {
		try {
			subjectRepository.checkSubject(subjectCode);
			return topicRepositoryImpl.getUnitBySubjectCode(subjectCode);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public UnitEntity getUnitByUnitNo(String unitNo) throws BusinessServiceException, NotFoundException {
		try {
			return topicRepositoryImpl.getUnitByUnitNo(unitNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public UnitEntity updateUnit(String unitNo, Unit unit) throws BusinessServiceException, NotFoundException {
		try {
			subjectRepository.checkSubject(unit.getSubject().getCode());
			return topicRepositoryImpl.updateUnit(unitNo, unit);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		}
	}

	@Override
	public UnitEntity deleteUnit(String unitNo) throws BusinessServiceException, NotFoundException {
		try {
			return topicRepositoryImpl.deleteUnit(unitNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public String getSubjectCode(String unitNo) throws BusinessServiceException, NotFoundException {
		try {
			return topicRepositoryImpl.getSubjectCode(unitNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

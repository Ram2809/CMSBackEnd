package com.curriculum.service.impl;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Qualification;
import com.curriculum.entity.QualificationEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.QualificationRepository;
import com.curriculum.service.QualificationService;

@Service
public class QualificationServiceImpl implements QualificationService {
	@Autowired
	private QualificationRepository qualificationRepository;

	@Override
	public Long addQualification(Qualification qualification) throws BusinessServiceException, NotFoundException {
		try {
			return qualificationRepository.addQualification(qualification);
		}catch(ConstraintViolationException e) {
			throw new ConstraintValidationException("Validation fails,Check your input!");
		}
		catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<QualificationEntity> getQualifications() throws BusinessServiceException {
		try {
			return qualificationRepository.getQualifications();
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public QualificationEntity deleteQualification(Long qualificationId) throws BusinessServiceException,NotFoundException {
		try {
			return qualificationRepository.deleteQualification(qualificationId);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

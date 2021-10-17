package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Qualification;
import com.curriculum.entity.QualificationEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface QualificationService {
	Long addQualification(Qualification qualification) throws BusinessServiceException, NotFoundException;

	List<QualificationEntity> getQualifications() throws BusinessServiceException;

	QualificationEntity deleteQualification(Long qualificationId) throws BusinessServiceException,NotFoundException;
}

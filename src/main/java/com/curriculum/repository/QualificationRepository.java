package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Qualification;
import com.curriculum.entity.QualificationEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface QualificationRepository {
	Long addQualification(Qualification qualification) throws DatabaseException;

	List<QualificationEntity> getQualifications() throws DatabaseException;

	QualificationEntity deleteQualification(Long qualificationId) throws DatabaseException, NotFoundException;
}

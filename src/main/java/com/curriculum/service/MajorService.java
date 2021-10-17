package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Major;
import com.curriculum.entity.MajorEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface MajorService {
	Long addMajor(Major major) throws BusinessServiceException;

	List<MajorEntity> getMajors() throws BusinessServiceException;
	
	MajorEntity deleteMajor(Long majorId) throws BusinessServiceException, NotFoundException;
}

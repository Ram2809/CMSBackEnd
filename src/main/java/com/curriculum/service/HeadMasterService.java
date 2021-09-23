package com.curriculum.service;

import org.springframework.http.ResponseEntity;

import com.curriculum.dto.HeadMaster;
import com.curriculum.entity.HeadMasterEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface HeadMasterService {
	Long addHeadMaster(HeadMaster headMaster) throws BusinessServiceException;

	HeadMasterEntity updateHeadMaster(Long id, HeadMaster headMaster) throws BusinessServiceException,NotFoundException;

	HeadMasterEntity deleteHeadMaster(Long id) throws BusinessServiceException, NotFoundException;

	HeadMasterEntity getHeadMaster(Long id) throws BusinessServiceException,NotFoundException;
}

package com.curriculum.service;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.HeadMaster;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.HeadMasterNotFoundException;

public interface HeadMasterService {
	HeadMaster addHeadMaster(HeadMaster headMasterDetails) throws BusinessServiceException;

	HeadMaster updateHeadMaster(Long id, HeadMaster headMasterDetails) throws BusinessServiceException;

	HeadMaster deleteHeadMaster(Long id) throws BusinessServiceException;

	HeadMaster getHeadMaster(Long id) throws BusinessServiceException;
}

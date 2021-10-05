package com.curriculum.service;

import com.curriculum.dto.HeadMaster;
import com.curriculum.entity.HeadMasterEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface HeadMasterService {
	Long addHeadMaster(HeadMaster headMaster) throws BusinessServiceException, NotFoundException;

	HeadMasterEntity updateHeadMaster(Long id, HeadMaster headMaster)
			throws BusinessServiceException, NotFoundException;

	HeadMasterEntity deleteHeadMaster(Long id) throws BusinessServiceException, NotFoundException;

	HeadMasterEntity getHeadMaster(String email) throws BusinessServiceException, NotFoundException;
}

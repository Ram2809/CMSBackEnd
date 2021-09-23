package com.curriculum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.HeadMaster;
import com.curriculum.entity.HeadMasterEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.HeadMasterRepository;
import com.curriculum.service.HeadMasterService;

@Service
public class HeadMasterServiceImpl implements HeadMasterService{
	@Autowired
	private HeadMasterRepository headMasterRepository;

	public Long addHeadMaster(HeadMaster headMaster) throws BusinessServiceException {
		try {
			return headMasterRepository.addHeadMaster(headMaster);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public HeadMasterEntity updateHeadMaster(Long id, HeadMaster headMaster) throws BusinessServiceException,NotFoundException {
		try {
			return headMasterRepository.updateHeadMaster(id, headMaster);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public HeadMasterEntity deleteHeadMaster(Long id) throws BusinessServiceException, NotFoundException {
		try {
			return headMasterRepository.deleteHeadMaster(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public HeadMasterEntity getHeadMaster(Long id) throws BusinessServiceException, NotFoundException {
		try {
			return headMasterRepository.getHeadMaster(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
}

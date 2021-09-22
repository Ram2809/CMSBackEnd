package com.curriculum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.entity.HeadMaster;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.repository.HeadMasterRepository;
import com.curriculum.service.HeadMasterService;

@Service
public class HeadMasterServiceImpl implements HeadMasterService{
	@Autowired
	private HeadMasterRepository headMasterRepository;

	public HeadMaster addHeadMaster(HeadMaster headMasterDetails) throws BusinessServiceException {
		try {
			return headMasterRepository.addHeadMaster(headMasterDetails);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public HeadMaster updateHeadMaster(Long id, HeadMaster headMasterDetails) throws BusinessServiceException {
		try {
			return headMasterRepository.updateHeadMaster(id, headMasterDetails);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public HeadMaster deleteHeadMaster(Long id) throws BusinessServiceException {
		try {
			return headMasterRepository.deleteHeadMaster(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	public HeadMaster getHeadMaster(Long id) throws BusinessServiceException {
		try {
			return headMasterRepository.getHeadMaster(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
}

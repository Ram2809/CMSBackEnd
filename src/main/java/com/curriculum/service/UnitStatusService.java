package com.curriculum.service;

import com.curriculum.dto.UnitStatus;
import com.curriculum.entity.UnitStatusEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface UnitStatusService {
	Long addUnitStatus(UnitStatus unitStatus) throws NotFoundException, BusinessServiceException;

	UnitStatusEntity getStatusByUnitNo(String unitNo, Long staffId, Long roomNo)
			throws NotFoundException, BusinessServiceException;

	UnitStatusEntity getUnitStatus(Long id) throws BusinessServiceException, NotFoundException;

	UnitStatusEntity updateUnitStatus(Long id, UnitStatus unitStatus)
			throws BusinessServiceException, NotFoundException;

	UnitStatusEntity deleteUnitStatus(Long id) throws BusinessServiceException, NotFoundException;
}

package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Unit;
import com.curriculum.entity.UnitEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface UnitService {
	String addUnit(Unit unit) throws BusinessServiceException, NotFoundException;

	List<UnitEntity> getUnitBySubjectCode(String subjectCode) throws BusinessServiceException, NotFoundException;

	UnitEntity getUnitByUnitNo(String unitNo) throws BusinessServiceException, NotFoundException;

	UnitEntity updateUnit(String unitNo, Unit unit) throws BusinessServiceException, NotFoundException;

	String getSubjectCode(String unitNo) throws BusinessServiceException, NotFoundException;

	UnitEntity deleteUnit(String unitNo) throws BusinessServiceException, NotFoundException;
}

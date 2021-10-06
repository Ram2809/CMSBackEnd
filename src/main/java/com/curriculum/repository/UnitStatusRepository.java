package com.curriculum.repository;

import com.curriculum.dto.UnitStatus;
import com.curriculum.entity.UnitStatusEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;
import com.curriculum.exception.NotFoundException;

public interface UnitStatusRepository {
	Long addUnitStatus(UnitStatus unitStatus) throws DatabaseException, NotAllowedException;

	UnitStatusEntity getStatusByUnitNo(String unitNo, Long staffId, Long roomNo) throws DatabaseException;

	UnitStatusEntity getUnitStatus(Long id) throws DatabaseException, NotFoundException;

	UnitStatusEntity updateUnitStatus(Long id, UnitStatus unitStatus) throws DatabaseException, NotFoundException;

	UnitStatusEntity deleteUnitStatus(Long id) throws DatabaseException, NotFoundException;

	void checkStatusId(Long id) throws NotFoundException;
}

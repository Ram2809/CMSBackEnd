package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Unit;
import com.curriculum.entity.UnitEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface UnitRepository {
	String addUnit(Unit unit) throws DatabaseException, NotFoundException;

	List<UnitEntity> getUnitBySubjectCode(String subjectCode) throws DatabaseException;

	UnitEntity getUnitByUnitNo(String unitNo) throws DatabaseException, NotFoundException;

	UnitEntity updateUnit(String unitNo, Unit unit) throws DatabaseException, NotFoundException;

	String getSubjectCode(String unitNo) throws DatabaseException, NotFoundException;

	UnitEntity deleteUnit(String unitNo) throws DatabaseException, NotFoundException;

	void checkUnit(String unitNo) throws NotFoundException;
}

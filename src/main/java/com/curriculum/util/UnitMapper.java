package com.curriculum.util;

import com.curriculum.dto.Unit;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.entity.UnitEntity;

public class UnitMapper {
	public static UnitEntity mapUnit(Unit unit) {
		UnitEntity unitEntity = new UnitEntity();
		unitEntity.setUnitNo(unit.getUnitNo());
		unitEntity.setUnitName(unit.getUnitName());
		unitEntity.setDescription(unit.getDescription());
		unitEntity.setMonth(unit.getMonth());
		SubjectEntity subjectEntity = new SubjectEntity();
		subjectEntity.setCode(unit.getSubject().getCode());
		unitEntity.setSubject(subjectEntity);
		return unitEntity;
	}
}

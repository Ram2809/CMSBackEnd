package com.curriculum.util;

import com.curriculum.dto.UnitStatus;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.entity.UnitEntity;
import com.curriculum.entity.UnitStatusEntity;

public class UnitStatusMapper {
	public static UnitStatusEntity mapUnitStatus(UnitStatus unitStatus) {
		UnitStatusEntity unitStatusEntity = new UnitStatusEntity();
		unitStatusEntity.setId(unitStatus.getId());
		unitStatusEntity.setBeginDate(unitStatus.getBeginDate());
		unitStatusEntity.setStatus(unitStatus.getStatus());
		unitStatusEntity.setCompletedDate(unitStatus.getCompletedDate());
		unitStatusEntity.setRemarks(unitStatus.getRemarks());
		UnitEntity unitEntity = new UnitEntity();
		unitEntity.setUnitNo(unitStatus.getUnit().getUnitNo());
		unitStatusEntity.setUnit(unitEntity);
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setId(unitStatus.getTeacher().getId());
		unitStatusEntity.setTeacher(teacherEntity);
		ClassEntity classEntity = new ClassEntity();
		classEntity.setRoomNo(unitStatus.getClassDetail().getRoomNo());
		unitStatusEntity.setClassDetail(classEntity);
		return unitStatusEntity;
	}
}

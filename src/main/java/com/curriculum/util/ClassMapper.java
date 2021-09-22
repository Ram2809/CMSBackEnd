package com.curriculum.util;

import com.curriculum.entity.ClassEntity;
import com.curriculum.dto.Class;
public class ClassMapper {
	public static ClassEntity mapClass(Class classDetail)
	{
		ClassEntity classEntity=new ClassEntity();
		classEntity.setRoomNo(classDetail.getRoomNo());
		classEntity.setStandard(classDetail.getStandard());
		classEntity.setSection(classDetail.getSection());
		return classEntity;
	}
}

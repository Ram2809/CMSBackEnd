package com.curriculum.util;

import com.curriculum.dto.Subject;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.SubjectEntity;

public class SubjectMapper {
	public static SubjectEntity subjectMapper(Subject subject)
	{
		SubjectEntity subjectEntity=new SubjectEntity();
		subjectEntity.setCode(subject.getCode());
		subjectEntity.setName(subject.getName());
		ClassEntity classEntity=new ClassEntity();
		classEntity.setRoomNo(subject.getClassRoom().getRoomNo());
		subjectEntity.setClassRoom(classEntity);
		return subjectEntity;
	}
}

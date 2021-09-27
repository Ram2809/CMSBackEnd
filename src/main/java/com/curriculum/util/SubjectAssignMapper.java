package com.curriculum.util;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.entity.SubjectEntity;

public class SubjectAssignMapper {
	public static SubjectAssignEntity mapSubjectAssign(SubjectAssign subjectAssign) {
		SubjectAssignEntity subjectAssignEntity = new SubjectAssignEntity();
		ClassEntity classEntity = new ClassEntity();
		classEntity.setRoomNo(subjectAssign.getClassDetail().getRoomNo());
		subjectAssignEntity.setClassDetail(classEntity);
		SubjectEntity subjectEntity = new SubjectEntity();
		subjectEntity.setCode(subjectAssign.getSubject().getCode());
		subjectAssignEntity.setSubject(subjectEntity);
		return subjectAssignEntity;
	}
}

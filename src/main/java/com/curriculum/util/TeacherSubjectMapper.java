package com.curriculum.util;

import com.curriculum.dto.TeacherAssign;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.entity.TeacherAssignEntity;

public class TeacherSubjectMapper {
	public static TeacherAssignEntity teacherSubjectMapper(TeacherAssign teacherAssign) {
		TeacherAssignEntity teacherAssignEntity = new TeacherAssignEntity();
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setId(teacherAssign.getTeacher().getId());
		teacherAssignEntity.setTeacher(teacherEntity);
		SubjectEntity subjectEntity = new SubjectEntity();
		subjectEntity.setCode(teacherAssign.getSubject().getCode());
		teacherAssignEntity.setSubject(subjectEntity);
		return teacherAssignEntity;
	}
}

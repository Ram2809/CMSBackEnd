package com.curriculum.util;

import com.curriculum.dto.TeacherAssign;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.entity.TeacherAssignEntity;
import com.curriculum.entity.TeacherEntity;

public class TeacherAssignMapper {
	public static TeacherAssignEntity teacherAssignMapper(TeacherAssign teacherAssign) {
		TeacherAssignEntity teacherAssignEntity = new TeacherAssignEntity();
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setId(teacherAssign.getTeacher().getId());
		teacherAssignEntity.setTeacher(teacherEntity);
		SubjectAssignEntity subjectAssignEntity = new SubjectAssignEntity();
		subjectAssignEntity.setId(teacherAssign.getSubjectAssign().getId());
		teacherAssignEntity.setSubjectAssign(subjectAssignEntity);
		return teacherAssignEntity;
	}
}

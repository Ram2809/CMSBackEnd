package com.curriculum.util;

import com.curriculum.dto.TeacherSubject;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.entity.TeacherSubjectEntity;

public class TeacherSubjectMapper {
	public static TeacherSubjectEntity teacherSubjectMapper(TeacherSubject teacherSubject) {
		TeacherSubjectEntity teacherSubjectEntity = new TeacherSubjectEntity();
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setId(teacherSubject.getTeacher().getId());
		teacherSubjectEntity.setTeacher(teacherEntity);
		SubjectEntity subjectEntity = new SubjectEntity();
		subjectEntity.setCode(teacherSubject.getSubject().getCode());
		teacherSubjectEntity.setSubject(subjectEntity);
		return teacherSubjectEntity;
	}
}

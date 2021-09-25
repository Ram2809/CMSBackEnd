package com.curriculum.util;

import com.curriculum.dto.Teacher;
import com.curriculum.entity.TeacherEntity;

public class TeacherMapper {
	public static TeacherEntity teacherMapper(Teacher teacher) {
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setId(teacher.getId());
		teacherEntity.setFirstName(teacher.getFirstName());
		teacherEntity.setLastName(teacher.getLastName());
		teacherEntity.setDateOfBirth(teacher.getDateOfBirth());
		teacherEntity.setGender(teacher.getGender());
		teacherEntity.setQualification(teacher.getQualification());
		teacherEntity.setMajor(teacher.getMajor());
		teacherEntity.setEmail(teacher.getEmail());
		teacherEntity.setContactNo(teacher.getContactNo());
		teacherEntity.setAddress(teacher.getAddress());
		return teacherEntity;
	}
}

package com.curriculum.util;

import com.curriculum.dto.StudentAssignDTO;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.StudentAssign;
import com.curriculum.entity.StudentEntity;

public class StudentAssignMapper {
	public static StudentAssign mapStudentAssign(StudentAssignDTO studentAssignDTO) {
		StudentAssign studentAssign=new StudentAssign();
		studentAssign.setId(studentAssignDTO.getId());
		studentAssign.setAcademicYear(studentAssignDTO.getAcademicYear());
		studentAssign.setStudentAddedOn(studentAssignDTO.getStudentAddedOn());
		studentAssign.setStudentLeftOn(studentAssignDTO.getStudentLeftOn());
		StudentEntity studentEntity=new StudentEntity();
		studentEntity.setRollNo(studentAssignDTO.getStudent().getRollNo());
		ClassEntity classEntity=new ClassEntity();
		classEntity.setRoomNo(studentAssignDTO.getClassDetail().getRoomNo());
		studentAssign.setStudent(studentEntity);
		studentAssign.setClassDetail(classEntity);
		return studentAssign;
	}
}

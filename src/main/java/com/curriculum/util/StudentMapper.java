package com.curriculum.util;

import com.curriculum.dto.Student;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.StudentEntity;

public class StudentMapper {
	public static StudentEntity studentMapper(Student student)
	{
		StudentEntity studentEntity=new StudentEntity();
		studentEntity.setRollNo(student.getRollNo());
		studentEntity.setFirstName(student.getFirstName());
		studentEntity.setLastName(student.getLastName());
		studentEntity.setDateOfBirth(student.getDateOfBirth());
		studentEntity.setGender(student.getGender());
		studentEntity.setContactNo(student.getContactNo());
		studentEntity.setAddress(student.getAddress());
		ClassEntity classEntity=new ClassEntity();
		classEntity.setRoomNo(student.getClassDetail().getRoomNo());
		studentEntity.setClassEntity(classEntity);
		return studentEntity;
	}
}

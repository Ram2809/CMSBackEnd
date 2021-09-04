package com.curriculum.repository.impl;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.curriculum.entity.TeacherSubject;
import com.curriculum.repository.TeacherSubjectRepository;
@Repository
public class TeacherSubjectRepositoryImpl implements TeacherSubjectRepository{

	@Override
	public ResponseEntity<String> assignTeacherSubject(Long teacherId, String subjectCode,
			TeacherSubject teacherSubjectDetails) {
		// TODO Auto-generated method stub
		return null;
	}

}

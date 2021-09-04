package com.curriculum.repository;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.TeacherSubject;

public interface TeacherSubjectRepository {
	ResponseEntity<String> assignTeacherSubject(Long teacherId,String subjectCode,TeacherSubject teacherSubjectDetails);
}

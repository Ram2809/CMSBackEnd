package com.curriculum.service;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.TeacherSubject;

public interface TeacherSubjectService {
	ResponseEntity<String> assignTeacherSubject(Long teacherId,String subjectCode,TeacherSubject teacherSubjectDetails);
}

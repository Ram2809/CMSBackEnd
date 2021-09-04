package com.curriculum.repository;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.TeacherSubject;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.TeacherNotFoundException;

public interface TeacherSubjectRepository {
	ResponseEntity<String> assignTeacherSubject(Long teacherId,String subjectCode,TeacherSubject teacherSubjectDetails) throws TeacherNotFoundException, SubjectNotFoundException;
	ResponseEntity<String> updateTeacherSubjectAssign(Long teacherId,String subjectCode,TeacherSubject teacherSubjectDetails) throws TeacherNotFoundException, SubjectNotFoundException;
	ResponseEntity<String> deleteTeacherSubjectAssign(Long teacherId,String subjectCode) throws TeacherNotFoundException, SubjectNotFoundException;
}

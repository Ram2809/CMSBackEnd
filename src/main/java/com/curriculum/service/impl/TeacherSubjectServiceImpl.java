package com.curriculum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.TeacherSubject;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.repository.TeacherSubjectRepository;
import com.curriculum.service.TeacherSubjectService;
@Service
public class TeacherSubjectServiceImpl implements TeacherSubjectService{
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepositoryImpl;
	@Override
	public ResponseEntity<String> assignTeacherSubject(Long teacherId, String subjectCode,
			TeacherSubject teacherSubjectDetails) throws TeacherNotFoundException, SubjectNotFoundException {
		// TODO Auto-generated method stub
		return teacherSubjectRepositoryImpl.assignTeacherSubject(teacherId,subjectCode,teacherSubjectDetails);
	}
	@Override
	public ResponseEntity<String> updateTeacherSubjectAssign(Long teacherId, String subjectCode,
			TeacherSubject teacherSubjectDetails) throws TeacherNotFoundException, SubjectNotFoundException {
		// TODO Auto-generated method stub
		return teacherSubjectRepositoryImpl.updateTeacherSubjectAssign(teacherId,subjectCode,teacherSubjectDetails);
	}
	@Override
	public ResponseEntity<String> deleteTeacherSubjectAssign(Long teacherId, String subjectCode) throws TeacherNotFoundException, SubjectNotFoundException {
		// TODO Auto-generated method stub
		return teacherSubjectRepositoryImpl.deleteTeacherSubjectAssign(teacherId,subjectCode);
	}

}

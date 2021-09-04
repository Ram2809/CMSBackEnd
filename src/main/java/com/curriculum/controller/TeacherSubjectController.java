package com.curriculum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.TeacherSubject;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.service.TeacherSubjectService;

@RestController
@RequestMapping("/teacherSubject")
public class TeacherSubjectController {
	@Autowired
	private TeacherSubjectService teacherSubjectServiceImpl;
	@PostMapping("/teacher/{id}/subject/{code}/assignTeacherSubject")
	public ResponseEntity<String> assignTeacherSubject(@PathVariable("id") Long teacherId,@PathVariable("code") String subjectCode,@RequestBody TeacherSubject teacherSubjectDetails) throws TeacherNotFoundException, SubjectNotFoundException
	{
		return teacherSubjectServiceImpl.assignTeacherSubject(teacherId,subjectCode,teacherSubjectDetails);
	}
	@PutMapping("/teacher/{id}/subject/{code}/updateTeacherSubjectAssign")
	public ResponseEntity<String> updateTeacherSubjectAssign(@PathVariable("id") Long teacherId,@PathVariable("code") String subjectCode,@RequestBody TeacherSubject teacherSubjectDetails) throws TeacherNotFoundException, SubjectNotFoundException
	{
		return teacherSubjectServiceImpl.updateTeacherSubjectAssign(teacherId,subjectCode,teacherSubjectDetails);
	}
	@DeleteMapping("/teacher/{id}/subject/{code}/deleteTeacherSubjectAssign")
	public ResponseEntity<String> deleteTeacherSubjectAssign(@PathVariable("id") Long teacherId,@PathVariable("code") String subjectCode) throws TeacherNotFoundException, SubjectNotFoundException
	{
		return teacherSubjectServiceImpl.deleteTeacherSubjectAssign(teacherId,subjectCode);
	}
}

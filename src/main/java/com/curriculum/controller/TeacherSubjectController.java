package com.curriculum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.TeacherSubject;
import com.curriculum.service.TeacherSubjectService;

@RestController
@RequestMapping("/teacherSubject")
public class TeacherSubjectController {
	@Autowired
	private TeacherSubjectService teacherSubjectServiceImpl;
	@PostMapping("/teacher/{id}/subject/{code}/assignTeacherSubject")
	public ResponseEntity<String> assignTeacherSubject(@PathVariable("id") Long teacherId,@PathVariable("code") String subjectCode,@RequestBody TeacherSubject teacherSubjectDetails)
	{
		return teacherSubjectServiceImpl.assignTeacherSubject(teacherId,subjectCode,teacherSubjectDetails);
	}
}

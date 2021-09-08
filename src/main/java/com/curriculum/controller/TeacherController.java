package com.curriculum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.curriculum.entity.Teacher;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.service.TeacherService;

@RestController
@RequestMapping("/teacher")
@CrossOrigin("http://localhost:4200")
public class TeacherController {
	@Autowired
	private TeacherService teacherServiceImpl;

	@PostMapping("/addTeacherDetails")
	public ResponseEntity<String> addTeacherDetails(@RequestBody Teacher teacherDetails) {
		return teacherServiceImpl.addTeacherDetails(teacherDetails);
	}

	@GetMapping("/getAllTeachers")
	public ResponseEntity<List<Teacher>> getAllTeacherDetails() {
		return teacherServiceImpl.getAllTeacherDetails();
	}

	@PutMapping("/updateTeacherDetails/{id}")
	public ResponseEntity<String> updateTeacherDetails(@PathVariable("id") Long id, @RequestBody Teacher teacherDetails)
			throws TeacherNotFoundException {
		try {
			return teacherServiceImpl.updateTeacherDetails(id, teacherDetails);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.OK);
		}
	}

	@DeleteMapping("/deleteTeacherDetails/{id}")
	public ResponseEntity<String> deleteTeacherDetails(@PathVariable("id") Long id) {
		try {
			return teacherServiceImpl.deleteTeacherDetails(id);
		} catch (BusinessServiceException e) {
			// TODO Auto-generated catch block
			return new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.OK);
		}
	}

	@GetMapping("/getTeacherDetails/{id}")
	public ResponseEntity<Teacher> getParticularTeacherDetails(@PathVariable("id") Long id) throws BusinessServiceException{
		return teacherServiceImpl.getParticularTeacherDetails(id);	 	
	}
}

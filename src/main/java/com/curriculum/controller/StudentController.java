package com.curriculum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.Student;
import com.curriculum.exception.StudentNotFoundException;
import com.curriculum.service.StudentService;

@RestController
@RequestMapping("/student")
@CrossOrigin("http://localhost:4200")
public class StudentController {
	@Autowired
	private StudentService studentServiceImpl;

	@PostMapping("/class/{roomNo}/addStudentDetails")
	public ResponseEntity<String> addStudentDetails(@PathVariable("roomNo") Long roomNo,
			@RequestBody Student studentDetails) {
		return studentServiceImpl.addStudentDetails(roomNo, studentDetails);
	}
	
	@GetMapping("/getAllStudentDetails")
	public ResponseEntity<List<Student>> getAllStudentDetails()
	{
		return studentServiceImpl.getAllStudentDetails();
	}
	
	@PutMapping("/updateStudentDetails/{rollNo}")
	public ResponseEntity<String> updateStudentDetails(@PathVariable("rollNo") Long rollNo,@RequestBody Student studentDetails) throws StudentNotFoundException
	{
		return studentServiceImpl.updateStudentDetails(rollNo,studentDetails);
	}
	
	@DeleteMapping("/deleteStudentDetails/{rollNo}")
	public ResponseEntity<String> deleteStudentDetails(@PathVariable("rollNo") Long rollNo) throws StudentNotFoundException
	{
		return studentServiceImpl.deleteStudentDetails(rollNo);
	}
	
	@GetMapping("/getStudentDetails/{rollNo}")
	public ResponseEntity<Student> getParticularStudentDetails(@PathVariable("rollNo") Long rollNo) throws StudentNotFoundException
	{
		return studentServiceImpl.getParticularStudentDetails(rollNo);
	}
	@GetMapping("/getStudentByClass/{roomNo}")
	public ResponseEntity<List<Student>> getStudentByClass(@PathVariable("roomNo") Long roomNo)
	{
		return studentServiceImpl.getStudentByClass(roomNo);
	}
}

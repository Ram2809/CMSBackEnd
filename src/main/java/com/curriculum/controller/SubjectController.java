package com.curriculum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.Subject;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.service.SubjectService;

@RestController
@RequestMapping("/subject")
@CrossOrigin("http://localhost:4200")
public class SubjectController {
	@Autowired
	private SubjectService subjectServiceImpl;

	@PostMapping("/class/{roomNo}/addSubjectDetails")
	public ResponseEntity<String> addSubjectDetails(@PathVariable("roomNo") Long roomNo,
			@RequestBody Subject subjectDetails) throws ClassNotFoundException {
		return subjectServiceImpl.addSubjectDetails(roomNo, subjectDetails);
	}

	@GetMapping("/getSubjectDetails")
	public ResponseEntity<List<Subject>> getAllSubjectDetails() {
		return subjectServiceImpl.getAllSubjectDetails();
	}

	@PutMapping("/class/{roomNo}/updateSubjectDetails/{code}")
	public ResponseEntity<String> updateSubjectDetails(@PathVariable("code") String code,
			@PathVariable("roomNo") Long roomNo, @RequestBody Subject subjectDetails)
			throws ClassNotFoundException, SubjectNotFoundException {
		return subjectServiceImpl.updateSubjectDetails(code, roomNo, subjectDetails);
	}

	@DeleteMapping("/deleteSubjectDetails/{code}")
	public ResponseEntity<String> deleteSubjectDetails(@PathVariable("code") String subjectCode)
			throws SubjectNotFoundException {
		return subjectServiceImpl.deleteSubjectDetails(subjectCode);
	}

	@GetMapping("/getParticularSubjectDetails/{code}")
	public ResponseEntity<Subject> getParticularSubjectDetails(@PathVariable("code") String subjectCode)
			throws SubjectNotFoundException {
		return subjectServiceImpl.getParticularSubjectDetails(subjectCode);
	}
	@GetMapping("/getSubjectByClass/{roomNo}")
	public ResponseEntity<List<Subject>> getSubjectByClass(@PathVariable("roomNo") Long roomNo) throws ClassNotFoundException
	{
		return subjectServiceImpl.getSubjectByClass(roomNo);
	}
	@GetMapping("/getSubjectName/{roomNo}")
	public ResponseEntity<List<String>> getSubjectName(@PathVariable("roomNo") Long roomNo) throws ClassNotFoundException
	{
		return subjectServiceImpl.getSubjectName(roomNo);
	}
	@GetMapping("/getSubjectCode/{roomNo}/{name}")
	public ResponseEntity<String> getSubjectCode(@PathVariable("roomNo") Long roomNo,@PathVariable("name") String name) throws ClassNotFoundException
	{
		return subjectServiceImpl.getSubjectCode(roomNo,name);
	}
	@ExceptionHandler(SubjectNotFoundException.class)
	public ResponseEntity<String> subjectNotFound(SubjectNotFoundException e)
	{
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(ClassNotFoundException.class)
	public ResponseEntity<String> classNotFound(ClassNotFoundException e)
	{
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
	
}

package com.curriculum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
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
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.service.SubjectService;
import com.curriculum.util.Response;

@RestController
@RequestMapping("api/subject")
@CrossOrigin("http://localhost:4200")
public class SubjectController {
	@Autowired
	private SubjectService subjectServiceImpl;

	@PostMapping
	public ResponseEntity<Response> addSubject(@RequestBody Subject subjectDetails) {
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		Subject subject=null;
		try {
			subject=subjectServiceImpl.addSubject(subjectDetails);
			response.setCode(200);
			response.setMessage("Subject details added successfully!");
			response.setData(subject);
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
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

	@GetMapping("/{code}")
	public ResponseEntity<Response> getParticularSubject(@PathVariable("code") String subjectCode)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		Subject subject=null;
		try {
			subject=subjectServiceImpl.getParticularSubject(subjectCode);
			if(subject!=null)
			{
				response.setCode(200);
				response.setMessage("Success!");
				response.setData(subject);
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				response.setCode(404);
				response.setMessage("Not Found!");
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
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

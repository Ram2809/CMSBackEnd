package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import com.curriculum.entity.ClassEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.service.ClassService;
import com.curriculum.util.Response;

@RestController
@RequestMapping("/api/class")
@CrossOrigin("http://localhost:4200")
public class ClassController {
	@Autowired
	private ClassService classServiceImpl;

	@PostMapping
	public ResponseEntity<Response> addClass(@RequestBody ClassEntity classDetails) {
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		ClassEntity classEntity=null;
		try {
			classEntity=classServiceImpl.addClass(classDetails);
			response.setCode(200);
			response.setMessage("Class details added successfully!");
			response.setData(classEntity);
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage("Internal Server Error");
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping//@JsonIgnore
	public ResponseEntity<Response> getAllClass() {
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		List<ClassEntity> classList=new ArrayList();
		try {
			classList=classServiceImpl.getAllClass();
			response.setCode(200);
			response.setMessage("Success");
			response.setData(classList);
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage("Internal Server Error!");
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	@PutMapping("/{roomNo}")//@JsonIgnore(x)
	public ResponseEntity<String> updateClassDetails(@PathVariable("roomNo") Long roomNo,@RequestBody ClassEntity classDetails)
	{
			return classServiceImpl.updateClassDetails(roomNo,classDetails);
	}
	
	@DeleteMapping("/deleteClassDetails/{roomNo}")
	public ResponseEntity<String> deleteClassDetails(@PathVariable("roomNo") Long roomNo)
	{
		return classServiceImpl.deleteClassDetails(roomNo);
	}
	@GetMapping("/getClassDetails/{roomNo}")
	public ResponseEntity<List<ClassEntity>> getParticularClassDetails(@PathVariable("roomNo") Long roomNo) throws ClassNotFoundException
	{
		return classServiceImpl.getParticularClassDetails(roomNo);
	}
	@GetMapping("/getSection/{standard}")
	public ResponseEntity<List<String>> getSection(@PathVariable("standard") String standard)
	{
		return classServiceImpl.getSection(standard);
	}
	@GetMapping("/getClass/{standard}/{section}")
	public ResponseEntity<Long> getClassDetails(@PathVariable("standard") String standard,@PathVariable("section") String section)
	{
		return classServiceImpl.getClassDetails(standard,section);
	}
}

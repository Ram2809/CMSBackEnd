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

import com.curriculum.entity.Student;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.StudentNotFoundException;
import com.curriculum.service.StudentService;
import com.curriculum.util.Response;

@RestController
@RequestMapping("api/student")
@CrossOrigin("http://localhost:4200")
public class StudentController {
	@Autowired
	private StudentService studentServiceImpl;

	@PostMapping
	public ResponseEntity<Response> addStudent(@RequestBody Student studentDetails) {
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		Student student=null;
		try {
			student=studentServiceImpl.addStudent(studentDetails);
			if(student!=null)
			{
				response.setCode(200);
				response.setMessage("Student Details Added Successfully!");
				response.setData(student);
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				response.setCode(500);
				response.setMessage("Internal Server Error!");
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
			}
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
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
	
	@DeleteMapping("/{rollNo}")
	public ResponseEntity<Response> deleteStudent(@PathVariable("rollNo") Long rollNo)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		Student student=null;
		try {
			student=studentServiceImpl.deleteStudent(rollNo);
			if(student!=null)
			{
				response.setCode(200);
				response.setMessage("Student is deleted successfully!");
				response.setData(student);
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				response.setCode(500);
				response.setMessage("Internal Server error");
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	
	@GetMapping("/getStudentDetails/{rollNo}")
	public ResponseEntity<Student> getParticularStudentDetails(@PathVariable("rollNo") Long rollNo) throws StudentNotFoundException
	{
		return studentServiceImpl.getParticularStudentDetails(rollNo);
	}
	@GetMapping("/{roomNo}")
	public ResponseEntity<Response> getStudentByClass(@PathVariable("roomNo") Long roomNo)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		List<Student> studentsList=new ArrayList<>();
		try {
			studentsList=studentServiceImpl.getStudentByClass(roomNo);
			if(!studentsList.isEmpty())
			{
				response.setCode(200);
				response.setMessage("Success");
				response.setData(studentsList);
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				response.setCode(404);
				response.setMessage("No Student Found!");
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
}

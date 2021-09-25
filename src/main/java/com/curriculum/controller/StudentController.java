package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.curriculum.dto.Student;
import com.curriculum.entity.StudentEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.StudentNotFoundException;
import com.curriculum.service.StudentService;
import com.curriculum.util.Response;

@RestController
@RequestMapping("api/student")
@CrossOrigin("http://localhost:4200")
public class StudentController {
	@Autowired
	private StudentService studentService;

	@PostMapping
	public ResponseEntity<Response> addStudent(@Valid @RequestBody Student student) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		Long rollNo = null;
		try {
			rollNo = studentService.addStudent(student);
			if (student != null) {
				response.setCode(200);
				response.setMessage("Student Details Added Successfully!");
				response.setData(student);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server Error!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof ClassNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

//	@GetMapping("/getAllStudentDetails")
//	public ResponseEntity<List<Student>> getAllStudentDetails()
//	{
//		return studentService.getAllStudentDetails();
//	}
//	
//	@PutMapping("/updateStudentDetails/{rollNo}")
//	public ResponseEntity<String> updateStudentDetails(@PathVariable("rollNo") Long rollNo,@RequestBody Student studentDetails) throws StudentNotFoundException
//	{
//		return studentService.updateStudentDetails(rollNo,studentDetails);
//	}
//	
	@DeleteMapping("/{rollNo}")
	public ResponseEntity<Response> deleteStudent(@PathVariable("rollNo") Long rollNo) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		StudentEntity studentEntity = null;
		try {
			studentEntity = studentService.deleteStudent(rollNo);
			if (studentEntity != null) {
				response.setCode(200);
				response.setMessage("Student is deleted successfully!");
				response.setData(studentEntity);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server error");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof StudentNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server error");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

//	@GetMapping("/getStudentDetails/{rollNo}")
//	public ResponseEntity<Student> getParticularStudentDetails(@PathVariable("rollNo") Long rollNo) throws StudentNotFoundException
//	{
//		return studentService.getParticularStudentDetails(rollNo);
//	}
	@GetMapping("/{roomNo}")
	public ResponseEntity<Response> getStudentByClass(@PathVariable("roomNo") Long roomNo) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		List<StudentEntity> studentsList = new ArrayList<>();
		try {
			studentsList = studentService.getStudentByClass(roomNo);
			if (!studentsList.isEmpty()) {
				response.setCode(200);
				response.setMessage("Success");
				response.setData(studentsList);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No Student Found!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server error");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> validationFailed(MethodArgumentNotValidException e) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		response.setCode(422);
		response.setMessage("Validation fails!");
		responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
		return responseEntity;
	}

}

package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.MethodArgumentNotValidException;

import com.curriculum.dto.Student;
import com.curriculum.entity.StudentEntity;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.StudentNotFoundException;
import com.curriculum.service.StudentService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/student")
@CrossOrigin("http://localhost:4200")
public class StudentController {
	@Autowired
	private StudentService studentService;
	private Logger logger = Logger.getLogger(StudentController.class);

	@PostMapping
	public ResponseEntity<Response> addStudent(@Valid @RequestBody Student student) {
		ResponseEntity<Response> responseEntity = null;
		Long rollNo = null;
		try {
			rollNo = studentService.addStudent(student);
			if (student != null) {
				student.setRollNo(rollNo);
				responseEntity = ResponseUtil.getResponse(200, "Student details deleted successfully!", student);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			if (e instanceof ConstraintValidationException) {
				responseEntity = ResponseUtil.getResponse(422, e.getMessage());
			} else {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
			}
		}
		return responseEntity;
	}

	@PutMapping("/{rollNo}")
	public ResponseEntity<Response> updateStudent(@PathVariable("rollNo") Long rollNo, @RequestBody Student student)
			throws StudentNotFoundException {
		ResponseEntity<Response> responseEntity = null;
		StudentEntity studentEntity = null;
		try {
			studentEntity = studentService.updateStudent(rollNo, student);
			responseEntity = ResponseUtil.getResponse(200, "Student Details Updated Successfully!", studentEntity);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			if (e instanceof ConstraintValidationException) {
				responseEntity = ResponseUtil.getResponse(422, e.getMessage());
			} else {
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
			}
		}
		return responseEntity;
	}

	@DeleteMapping("/{rollNo}")
	public ResponseEntity<Response> deleteStudent(@PathVariable("rollNo") Long rollNo) {
		ResponseEntity<Response> responseEntity = null;
		StudentEntity studentEntity = null;
		try {
			studentEntity = studentService.deleteStudent(rollNo);
			if (studentEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Student is deleted successfully!", studentEntity);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping
	public ResponseEntity<Response> getStudent(@RequestParam("rollNo") Long rollNo) {
		ResponseEntity<Response> responseEntity = null;
		StudentEntity studentEntity = null;
		try {
			studentEntity = studentService.getStudent(rollNo);
			responseEntity = ResponseUtil.getResponse(200, "Success!", studentEntity);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/{roomNo}")
	public ResponseEntity<Response> getStudentByClass(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		List<StudentEntity> studentsList = new ArrayList<>();
		try {
			studentsList = studentService.getStudentByClass(roomNo);
			if (!studentsList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", studentsList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Student Found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> validationFailed(MethodArgumentNotValidException e) {
		logger.error("Validation fails, Check your input!");
		ResponseEntity<Response> responseEntity = null;
		responseEntity = ResponseUtil.getResponse(422, "Validation fails!");
		return responseEntity;
	}

}

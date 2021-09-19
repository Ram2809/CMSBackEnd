package com.curriculum.controller;

import java.util.ArrayList;
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
import com.curriculum.util.Response;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin("http://localhost:4200")
public class TeacherController {
	@Autowired
	private TeacherService teacherServiceImpl;

	@PostMapping
	public ResponseEntity<Response> addTeacher(@RequestBody Teacher teacher) {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		Teacher teacherDetail = null;
		try {
			teacherDetail = teacherServiceImpl.addTeacher(teacher);
			response.setCode(200);
			response.setMessage("Teacher Details Added Successfully!");
			response.setData(teacherDetail);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage("Internal Server Error");
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping
	public ResponseEntity<Response> getAllTeacher() {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		List<Teacher> teacherList = new ArrayList();
		try {
			teacherList = teacherServiceImpl.getAllTeacher();
			if (!teacherList.isEmpty()) {
				response.setCode(200);
				response.setMessage("Teacher List fetched successfully!");
				response.setData(teacherList);
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("Not Found!");
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
			}
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage("Internal Server Error");
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> updateTeacher(@PathVariable("id") Long id, @RequestBody Teacher teacherDetails) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		Teacher teacher = new Teacher();
		try {
			teacher = teacherServiceImpl.updateTeacher(id, teacherDetails);
			response.setCode(200);
			response.setMessage("Teacher details updated successfully!");
			response.setData(teacher);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage("Teacher Not Found with" + " " + id + "!");
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		}
		return responseEntity;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteTeacher(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		Teacher teacher = new Teacher();
		try {
			teacher = teacherServiceImpl.deleteTeacher(id);
			if (teacher != null) {
				response.setCode(200);
				response.setMessage("Teacher details deleted successfully!");
				response.setData(teacher);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server Error");
				response.setData(teacher);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage("Teacher Not Found with" + " " + id + "!");
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getParticularTeacher(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		Teacher teacher = new Teacher();
		try {
			teacher = teacherServiceImpl.getParticularTeacher(id);
			response.setData(teacher);
			response.setCode(400);
			response.setMessage("Success");
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setMessage(e.getMessage());
			response.setCode(404);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
}

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

import com.curriculum.dto.Teacher;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.service.TeacherService;
import com.curriculum.util.Response;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin("http://localhost:4200")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;

	@PostMapping
	public ResponseEntity<Response> addTeacher(@RequestBody Teacher teacher) {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		Long staffId = null;
		try {
			staffId = teacherService.addTeacher(teacher);
			if (staffId > 0) {
				response.setCode(200);
				response.setMessage("Teacher Details Added Successfully!");
				response.setData(teacher);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server Error");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping
	public ResponseEntity<Response> getAllTeacher() {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		List<TeacherEntity> teacherList = new ArrayList<>();
		try {
			teacherList = teacherService.getAllTeacher();
			if (!teacherList.isEmpty()) {
				response.setCode(200);
				response.setMessage("Teacher List fetched successfully!");
				response.setData(teacherList);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No Teacher Found!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			}
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> updateTeacher(@PathVariable("id") Long id, @RequestBody Teacher teacher) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		TeacherEntity teacherEntity = null;
		try {
			teacherEntity = teacherService.updateTeacher(id, teacher);
			response.setCode(200);
			response.setMessage("Teacher details updated successfully!");
			response.setData(teacherEntity);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteTeacher(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		TeacherEntity teacherEntity = null;
		try {
			teacherEntity = teacherService.deleteTeacher(id);
			if (teacherEntity != null) {
				response.setCode(200);
				response.setMessage("Teacher details deleted successfully!");
				response.setData(teacherEntity);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server Error");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getParticularTeacher(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		TeacherEntity teacherEntity = null;
		try {
			teacherEntity = teacherService.getParticularTeacher(id);
			response.setData(teacherEntity);
			response.setCode(200);
			response.setMessage("Success");
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			response.setMessage(e.getMessage());
			response.setCode(404);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
}

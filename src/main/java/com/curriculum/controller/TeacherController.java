package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.Teacher;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.TeacherService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping("/api/teacher")
@CrossOrigin("http://localhost:4200")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	private Logger logger = Logger.getLogger(TeacherController.class);

	@PostMapping
	public ResponseEntity<Response> addTeacher(@Valid @RequestBody Teacher teacher) {
		ResponseEntity<Response> responseEntity = null;
		Long staffId = null;
		try {
			staffId = teacherService.addTeacher(teacher);
			if (staffId > 0) {
				teacher.setId(staffId);
				responseEntity = ResponseUtil.getResponse(200, "Teacher Details Added Successfully!", teacher);
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

	@GetMapping
	public ResponseEntity<Response> getAllTeacher() {
		ResponseEntity<Response> responseEntity = null;
		List<TeacherEntity> teachersList = new ArrayList<>();
		try {
			teachersList = teacherService.getAllTeacher();
			if (!teachersList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", teachersList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Teacher Found!", teachersList);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		}
		return responseEntity;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> updateTeacher(@PathVariable("id") Long id, @RequestBody Teacher teacher) {
		ResponseEntity<Response> responseEntity = null;
		TeacherEntity teacherEntity = null;
		try {
			teacherEntity = teacherService.updateTeacher(id, teacher);
			responseEntity = ResponseUtil.getResponse(200, "Teacher Details Updated Successfully!", teacherEntity);
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

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteTeacher(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		TeacherEntity teacherEntity = null;
		try {
			teacherEntity = teacherService.deleteTeacher(id);
			if (teacherEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Teacher Details Deleted Successfully!", teacherEntity);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", teacherEntity);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getParticularTeacher(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		TeacherEntity teacherEntity = null;
		try {
			teacherEntity = teacherService.getParticularTeacher(id);
			responseEntity = ResponseUtil.getResponse(200, "Success!", teacherEntity);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/email/{email}")
	public ResponseEntity<Response> getTeacherByEmail(@PathVariable("email") String email) {
		ResponseEntity<Response> responseEntity = null;
		TeacherEntity teacherEntity = null;
		try {
			teacherEntity = teacherService.getTeacherByEmail(email);
			responseEntity = ResponseUtil.getResponse(200, "Success!", teacherEntity);
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

package com.curriculum.controller;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.TeacherAssign;
import com.curriculum.entity.TeacherAssignEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.TeacherAssignService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/teacherassign")
@CrossOrigin("http://localhost:4200")
public class TeacherAssignController {
	@Autowired
	private TeacherAssignService teacherAssignService;

	@PostMapping
	public ResponseEntity<Response> assignTeacherSubject(@RequestBody TeacherAssign teacherAssign) {
		ResponseEntity<Response> responseEntity = null;
		Long assignId = null;
		try {
			assignId = teacherAssignService.assignTeacherSubject(teacherAssign);
			teacherAssign.setId(assignId);
			responseEntity = ResponseUtil.getResponse(200, "Teacher assigned for subject successfully!", teacherAssign);
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

	@PutMapping("/{id}")
	public ResponseEntity<Response> updateTeacherSubjectAssign(@PathVariable("id") Long id,
			@RequestBody TeacherAssign teacherAssign) {
		ResponseEntity<Response> responseEntity = null;
		TeacherAssignEntity teacherAssignEntity = null;
		try {
			teacherAssignEntity = teacherAssignService.updateTeacherSubjectAssign(id, teacherAssign);
			responseEntity = ResponseUtil.getResponse(200, "Teacher assigned for subject successfully!",
					teacherAssignEntity);
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
	public ResponseEntity<Response> deleteTeacherSubjectAssign(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		TeacherAssignEntity teacherSubjectAssign = null;
		try {
			teacherSubjectAssign = teacherAssignService.deleteTeacherSubjectAssign(id);
			if (teacherSubjectAssign != null) {
				responseEntity = ResponseUtil.getResponse(200, "Teacher is deleted for subject successfully!",
						teacherSubjectAssign);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", teacherSubjectAssign);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping
	public ResponseEntity<Response> getTeacherSubjectAssign(@RequestParam("staffId") Long staffId) {
		ResponseEntity<Response> responseEntity = null;
		List<Long> subjectAssignIdList = null;
		try {
			subjectAssignIdList = teacherAssignService.getSubjectAssignIds(staffId);
			if (!subjectAssignIdList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", subjectAssignIdList);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", subjectAssignIdList);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getTeacherId(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		Long teacherId = null;
		try {
			teacherId = teacherAssignService.getTeacherId(id);
			if (teacherId != null) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", teacherId);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", teacherId);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		}
//		} catch (NotFoundException e) {
//			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
//		}
		return responseEntity;
	}

}
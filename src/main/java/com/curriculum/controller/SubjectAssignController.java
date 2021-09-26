package com.curriculum.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.SubjectAssignService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/subjectassign")
@CrossOrigin("http://localhost:4200")
public class SubjectAssignController {

	@Autowired
	private SubjectAssignService subjectAssignService;
	private Logger logger = Logger.getLogger(SubjectAssignController.class);

	@PostMapping
	public ResponseEntity<Response> addSubjectAssign(@RequestBody SubjectAssign subjectAssign) {
		ResponseEntity<Response> responseEntity = null;
		Long id = null;
		try {
			id = subjectAssignService.addSubjectAssign(subjectAssign);
			if (id > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Subject assigned for class  successfully!",
						subjectAssign);
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
}

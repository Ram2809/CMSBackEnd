package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.Qualification;
import com.curriculum.entity.QualificationEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.QualificationService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/qualification")
@Slf4j
public class QualificationController {
	
	@Autowired
	private QualificationService qualificationService;
	@PostMapping
	public ResponseEntity<Response> addQualification(@Valid @RequestBody Qualification qualification){
		ResponseEntity<Response> responseEntity=null;
		try {
			Long id=qualificationService.addQualification(qualification);
			qualification.setId(id);
			if(id>0) {
				responseEntity=ResponseUtil.getResponse(200, "Qualification added successfully!", qualification);
			}
			else {
				responseEntity=ResponseUtil.getResponse(500, "Internal Server Error!", qualification);
			}
		}catch(NotFoundException e) {
			if(e instanceof ConstraintValidationException) {
				responseEntity=ResponseUtil.getResponse(422, e.getMessage(), qualification);
			}
		}
		catch(BusinessServiceException e) {
			responseEntity=ResponseUtil.getResponse(500, e.getMessage(), qualification);
		}
		return responseEntity;
	}
	@GetMapping
	public ResponseEntity<Response> getQualifications(){
		ResponseEntity<Response> responseEntity=null;
		List<QualificationEntity> qualificationList=new ArrayList<>();
		try {
			qualificationList=qualificationService.getQualifications();
			if(!qualificationList.isEmpty()) {
				responseEntity=ResponseUtil.getResponse(200, "Qualifications are fetched successfully!", qualificationList);
			}
			else {
				responseEntity=ResponseUtil.getResponse(404, "No Qualification Found!", qualificationList);
			}
		}catch(BusinessServiceException e) {
			responseEntity=ResponseUtil.getResponse(500, e.getMessage(), qualificationList);
		}
		return responseEntity;
	}
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> validationFailed(MethodArgumentNotValidException e) {
		log.error("Validation fails, Check your input!");
		ResponseEntity<Response> responseEntity = null;
		responseEntity = ResponseUtil.getResponse(422, "Validation fails!");
		return responseEntity;
	}
}

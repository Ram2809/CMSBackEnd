package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.Major;
import com.curriculum.entity.MajorEntity;
import com.curriculum.entity.QualificationEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.MajorService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/major")
@Slf4j
@CrossOrigin("http://localhost:4200")
public class MajorController {
	@Autowired
	private MajorService majorService;
	@PostMapping
	public ResponseEntity<Response> addMajor(@Valid @RequestBody Major major) {
		ResponseEntity<Response> responseEntity = null;
		Long majorId = 0l;
		try {
			majorId = majorService.addMajor(major);
			major.setId(majorId);
			if (majorId > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Major added successfully!", major);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", major);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), major);
		}
		return responseEntity;
	}
	@GetMapping
	public ResponseEntity<Response> getMajors(){
		ResponseEntity<Response> responseEntity=null;
		List<MajorEntity> majorList=new ArrayList<>();
		try {
			majorList=majorService.getMajors();
			if(!majorList.isEmpty()) {
				responseEntity=ResponseUtil.getResponse(200, "Majors are fetched successfully!", majorList);
			}
			else {
				responseEntity=ResponseUtil.getResponse(404, "No Major Found!", majorList);
			}
		}catch(BusinessServiceException e) {
			responseEntity=ResponseUtil.getResponse(500, e.getMessage(), majorList);
		}
		return responseEntity;
	}
	@DeleteMapping("/{majorId}")
	public ResponseEntity<Response> deleteMajor(@PathVariable("majorId") Long majorId){
		ResponseEntity<Response> responseEntity=null;
		MajorEntity majorEntity=null;
		try {
			majorEntity=majorService.deleteMajor(majorId);
			if(majorEntity!=null) {
				responseEntity = ResponseUtil.getResponse(200, "Major deleted successfully!", majorEntity);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", majorEntity);
			}
		}catch(BusinessServiceException e) {
			responseEntity=ResponseUtil.getResponse(500, e.getMessage(), majorEntity);
		}catch(NotFoundException e) {
			responseEntity=ResponseUtil.getResponse(404, e.getMessage(), majorEntity);
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

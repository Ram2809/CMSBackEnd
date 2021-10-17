package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.StudentAssignDTO;
import com.curriculum.entity.StudentAssign;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.StudentAssignService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("api/studentassign")
@Slf4j
public class StudentAssignController {
	@Autowired
	private StudentAssignService studentAssignService;

	@PostMapping
	public ResponseEntity<Response> addStudentAssign(@Valid @RequestBody StudentAssignDTO studentAssignDTO) {
		ResponseEntity<Response> responseEntity = null;
		Long assignId = 0l;
		try {
			assignId = studentAssignService.addStudentAssign(studentAssignDTO);
			studentAssignDTO.setId(assignId);
			if (assignId > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Student assigned for class successfully!",
						studentAssignDTO);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", studentAssignDTO);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), studentAssignDTO);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), studentAssignDTO);
		}
		return responseEntity;
	}
	@GetMapping("/{roomNo}/{academicYear}")
	public ResponseEntity<Response> getStudentClassDetails(@PathVariable("roomNo") Long roomNo,@PathVariable("academicYear") String academicYear){
		ResponseEntity<Response> responseEntity=null;
		List<StudentAssign> studentAssignList=new ArrayList<>();
		try {
			studentAssignList=studentAssignService.getStudentClassDetails(roomNo,academicYear);
			if(!studentAssignList.isEmpty()) {
				responseEntity=ResponseUtil.getResponse(200, "Success", studentAssignList);
			}
			else {
				responseEntity=ResponseUtil.getResponse(404, "Not Found!", studentAssignList);
			}
		}catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(),studentAssignList);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), studentAssignList);
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

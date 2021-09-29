package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.entity.SubjectEntity;
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

	@GetMapping("/{roomNo}")
	public ResponseEntity<Response> getSubjects(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		List<SubjectAssignEntity> subjectList = new ArrayList<>();
		try {
			subjectList = subjectAssignService.getSubjects(roomNo);
			if (!subjectList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", subjectList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No subject name found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/{roomNo}/{code}")
	public ResponseEntity<Response> getAssignId(@PathVariable("roomNo") Long roomNo,
			@PathVariable("code") String subjectCode) {
		ResponseEntity<Response> responseEntity = null;
		Long assignId = null;
		try {
			assignId = subjectAssignService.getAssignId(roomNo, subjectCode);
			if (assignId != 0) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", assignId);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Assign Id found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/subject/{id}/{roomNo}")
	public ResponseEntity<Response> getSubjectCode(@PathVariable("id") Long id,@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		String subjectCode = null;
		try {
			subjectCode = subjectAssignService.getSubjectCode(id,roomNo);
			if (subjectCode != null) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", subjectCode);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Assign Id found!"+" "+id+"!", subjectCode);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}
	@GetMapping("/subject/{id}")
	public ResponseEntity<Response> getRoomNo(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		Long roomNo = null;
		try {
			roomNo = subjectAssignService.getRoomNo(id);
			if (roomNo != 0) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", roomNo);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Assign Id found!"+" "+id+"!", roomNo);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}
}

package com.curriculum.controller;


import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
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

import com.curriculum.dto.Subject;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.SubjectService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/subject")
@CrossOrigin("http://localhost:4200")
public class SubjectController {
	@Autowired
	private SubjectService subjectService;
	private Logger logger = Logger.getLogger(SubjectController.class);

	@PostMapping
	public ResponseEntity<Response> addSubject(@Valid @RequestBody Subject subject) {
		ResponseEntity<Response> responseEntity = null;
		String subjectCode = null;
		try {
			subjectCode = subjectService.addSubject(subject);
			if (subjectCode.length() > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Subject details added successfully!", subject);
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

//	@GetMapping("/getSubjectDetails")
//	public ResponseEntity<List<Subject>> getAllSubjectDetails() {
//		return subjectService.getAllSubjectDetails();
//	}

	@PutMapping("/{code}")
	public ResponseEntity<Response> updateSubject(@PathVariable("code") String code, @RequestBody Subject subject) {
		ResponseEntity<Response> responseEntity = null;
		SubjectEntity subjectEntity = null;
		try {
			subjectEntity = subjectService.updateSubject(code, subject);
			responseEntity = ResponseUtil.getResponse(200, "Subject details updated successfully!", subjectEntity);
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

	@DeleteMapping("/{code}")
	public ResponseEntity<Response> deleteSubject(@PathVariable("code") String subjectCode) {
		ResponseEntity<Response> responseEntity = null;
		SubjectEntity subjectEntity = null;
		try {
			subjectEntity = subjectService.deleteSubject(subjectCode);
			if (subjectEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Subject details deleted successfully!", subjectEntity);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/{code}")
	public ResponseEntity<Response> getParticularSubject(@PathVariable("code") String subjectCode) {
		ResponseEntity<Response> responseEntity = null;
		SubjectEntity subjectEntity = null;
		try {
			subjectEntity = subjectService.getParticularSubject(subjectCode);
			if (subjectEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", subjectEntity);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Subject Found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping
	public ResponseEntity<Response> getSubjectByClass(@RequestParam("roomNo") Long roomNo) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		List<SubjectEntity> subjectList = new ArrayList<>();
		try {
			subjectList = subjectService.getSubjectByClass(roomNo);
			if (!subjectList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", subjectList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No subject found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("class/{roomNo}")
	public ResponseEntity<Response> getSubjectName(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		List<String> subjectNames = new ArrayList<>();
		try {
			subjectNames = subjectService.getSubjectName(roomNo);
			if (!subjectNames.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", subjectNames);
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

	@GetMapping("/{roomNo}/{name}")
	public ResponseEntity<Response> getSubjectCode(@PathVariable("roomNo") Long roomNo,
			@PathVariable("name") String name) {
		ResponseEntity<Response> responseEntity = null;
		String code = null;
		try {
			code = subjectService.getSubjectCode(roomNo, name);
			if (code != null) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", code);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No subject code found!");
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

package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

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

import com.curriculum.dto.Subject;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.service.SubjectService;
import com.curriculum.util.Response;
import com.curriculum.exception.ConstraintValidationException;

@RestController
@RequestMapping("api/subject")
@CrossOrigin("http://localhost:4200")
public class SubjectController {
	@Autowired
	private SubjectService subjectService;

	@PostMapping
	public ResponseEntity<Response> addSubject(@Valid @RequestBody Subject subject) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		String subjectCode = null;
		try {
			subjectCode = subjectService.addSubject(subject);
			if (subjectCode.length() > 0) {
				response.setCode(200);
				response.setMessage("Subject details added successfully!");
				response.setData(subject);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server Error!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof ClassNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			if (e instanceof ConstraintValidationException) {
				response.setCode(422);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
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
		Response response = new Response();
		ResponseEntity responseEntity = null;
		SubjectEntity subjectEntity = null;
		try {
			subjectEntity = subjectService.updateSubject(code, subject);
			response.setCode(200);
			response.setMessage("Subject details updated successfully!");
			response.setData(subjectEntity);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@DeleteMapping("/{code}")
	public ResponseEntity<Response> deleteSubject(@PathVariable("code") String subjectCode) {
		Response response = new Response();
		ResponseEntity responseEntity = null;
		SubjectEntity subjectEntity = null;
		try {
			subjectEntity = subjectService.deleteSubject(subjectCode);
			if (subjectEntity != null) {
				response.setCode(200);
				response.setMessage("Subject details deleted successfully!");
				response.setData(subjectEntity);
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(),
						HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@GetMapping("/{code}")
	public ResponseEntity<Response> getParticularSubject(@PathVariable("code") String subjectCode) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		SubjectEntity subjectEntity = null;
		try {
			subjectEntity = subjectService.getParticularSubject(subjectCode);
			if (subjectEntity != null) {
				response.setCode(200);
				response.setMessage("Success!");
				response.setData(subjectEntity);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No Subject Found!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof SubjectNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server Error!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@GetMapping
	public ResponseEntity<Response> getSubjectByClass(@RequestParam("roomNo") Long roomNo)
			throws ClassNotFoundException {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		List<SubjectEntity> subjectList = new ArrayList<>();
		try {
			subjectList = subjectService.getSubjectByClass(roomNo);
			if (!subjectList.isEmpty()) {
				response.setCode(200);
				response.setMessage("Success!");
				response.setData(subjectList);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No Subject Found!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@GetMapping("class/{roomNo}")
	public ResponseEntity<Response> getSubjectName(@PathVariable("roomNo") Long roomNo) {
		Response response = new Response();
		ResponseEntity responseEntity = null;
		List<String> subjectNames = new ArrayList();
		try {
			subjectNames = subjectService.getSubjectName(roomNo);
			if (!subjectNames.isEmpty()) {
				response.setCode(200);
				response.setMessage("Success!");
				response.setData(subjectNames);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No Subject Name Found!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@GetMapping("/{roomNo}/{name}")
	public ResponseEntity<String> getSubjectCode(@PathVariable("roomNo") Long roomNo,
			@PathVariable("name") String name) {
		Response response = new Response();
		ResponseEntity responseEntity = null;
		String code = null;
		try {
			code = subjectService.getSubjectCode(roomNo, name);
			if (code != null) {
				response.setCode(200);
				response.setMessage("Success!");
				response.setData(code);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No subject code found!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

}

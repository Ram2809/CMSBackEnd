package com.curriculum.controller;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.ClassEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.service.ClassService;
import com.curriculum.util.Response;

@RestController
@RequestMapping("/api/class")
@CrossOrigin("http://localhost:4200")
public class ClassController {
	@Autowired
	private ClassService classService;

	@PostMapping
	public ResponseEntity<Response> addClass(@RequestBody ClassEntity classDetails) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		ClassEntity classEntity = null;
		try {
			classEntity = classService.addClass(classDetails);
			response.setCode(200);
			response.setMessage("Class details added successfully!");
			response.setData(classEntity);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage("Internal Server Error");
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping // @JsonIgnore
	public ResponseEntity<Response> getAllClass() {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		List<ClassEntity> classList = new ArrayList<ClassEntity>();
		try {
			classList = classService.getAllClass();
			response.setCode(200);
			response.setMessage("Success");
			response.setData(classList);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage("Internal Server Error!");
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(),
					HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@PutMapping("/{roomNo}") // @JsonIgnore(x)
	public ResponseEntity<Response> updateClass(@PathVariable("roomNo") Long roomNo,
			@RequestBody ClassEntity classDetails) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		ClassEntity classEntity = null;
		try {
			classEntity = classService.updateClass(roomNo, classDetails);
			response.setCode(200);
			response.setMessage("Class details updated successfully!");
			response.setData(classEntity);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@DeleteMapping("/{roomNo}")
	public ResponseEntity<Response> deleteClass(@PathVariable("roomNo") Long roomNo) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		ClassEntity classEntity = null;
		try {
			classEntity = classService.deleteClass(roomNo);
			response.setCode(200);
			response.setMessage("Class details deleted successfully!");
			response.setData(classEntity);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@GetMapping("/{roomNo}")
	public ResponseEntity<Response> getParticularClass(@PathVariable("roomNo") Long roomNo) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		ClassEntity classEntity = null;
		try {
			classEntity = classService.getParticularClass(roomNo);
			response.setCode(200);
			response.setMessage("Success");
			response.setData(classEntity);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@GetMapping("/standard/{standard}")
	public ResponseEntity<Response> getSection(@PathVariable("standard") String standard) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		List<String> sectionList = null;
		try {
			sectionList = classService.getSection(standard);
			response.setCode(200);
			response.setMessage("Success");
			response.setData(sectionList);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}

	@GetMapping("/{standard}/{section}")
	public ResponseEntity<Response> getClassRoomNo(@PathVariable("standard") String standard,
			@PathVariable("section") String section) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		Long roomNo = null;
		try {
			roomNo = classService.getClassRoomNo(standard, section);
			response.setCode(200);
			response.setMessage("Success");
			response.setData(roomNo);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
}

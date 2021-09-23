package com.curriculum.controller;

import java.sql.SQLException;
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
import com.curriculum.dto.Class;
import com.curriculum.entity.ClassEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.ClassService;
import com.curriculum.util.Response;

@RestController
@RequestMapping("/api/class")
@CrossOrigin("http://localhost:4200")
public class ClassController {
	@Autowired
	private ClassService classService;

	@PostMapping
	public ResponseEntity<Response> addClass(@RequestBody Class classDetail) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		Long roomNo = null;
		try {
			roomNo = classService.addClass(classDetail);
			if (roomNo > 0) {
				response.setCode(200);
				response.setMessage("Class details added successfully!");
				response.setData(classDetail);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			}
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
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
			if (!classList.isEmpty()) {
				response.setCode(200);
				response.setMessage("Success");
				response.setData(classList);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No class rooms found!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage("Internal Server Error!");
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@PutMapping("/{roomNo}") // @JsonIgnore(x)
	public ResponseEntity<Response> updateClass(@PathVariable("roomNo") Long roomNo, @RequestBody Class classDetail) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		ClassEntity classEntity = null;
		try {
			classEntity = classService.updateClass(roomNo, classDetail);
			response.setCode(200);
			response.setMessage("Class details updated successfully!");
			response.setData(classEntity);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
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

	@DeleteMapping("/{roomNo}")
	public ResponseEntity<Response> deleteClass(@PathVariable("roomNo") Long roomNo) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		ClassEntity classEntity = null;
		try {
			classEntity = classService.deleteClass(roomNo);
			if (classEntity != null) {
				response.setCode(200);
				response.setMessage("Class details deleted successfully!");
				response.setData(classEntity);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server Error!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
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
			if (classEntity != null) {
				response.setCode(200);
				response.setMessage("Success");
				response.setData(classEntity);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No data found!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof ClassNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
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
			if (!sectionList.isEmpty()) {
				response.setCode(200);
				response.setMessage("Success");
				response.setData(sectionList);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No section found for given standard!");
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
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
			if (roomNo != null) {
				response.setCode(200);
				response.setMessage("Success");
				response.setData(roomNo);
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No class room found!");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
}

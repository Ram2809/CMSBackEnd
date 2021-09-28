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
import org.springframework.web.bind.annotation.RestController;
import com.curriculum.dto.Class;
import com.curriculum.entity.ClassEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.ClassService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("/api/class")
@CrossOrigin("http://localhost:4200")
public class ClassController {
	@Autowired
	private ClassService classService;
	private Logger logger = Logger.getLogger(ClassController.class);

	@PostMapping
	public ResponseEntity<Response> addClass(@Valid @RequestBody Class classDetail) {
		ResponseEntity<Response> responseEntity = null;
		Long roomNo = null;
		try {
			roomNo = classService.addClass(classDetail);
			if (roomNo > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", classDetail);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			if (e instanceof ConstraintValidationException)
				responseEntity = ResponseUtil.getResponse(422, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping // @JsonIgnore
	public ResponseEntity<Response> getAllClass() {
		ResponseEntity<Response> responseEntity = null;
		List<ClassEntity> classList = new ArrayList<>();
		try {
			classList = classService.getAllClass();
			if (!classList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", classList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No class rooms found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		}
		return responseEntity;
	}

	@PutMapping("/{roomNo}") // @JsonIgnore(x)
	public ResponseEntity<Response> updateClass(@PathVariable("roomNo") Long roomNo, @RequestBody Class classDetail) {
		ResponseEntity<Response> responseEntity = null;
		ClassEntity classEntity = null;
		try {
			classEntity = classService.updateClass(roomNo, classDetail);
			responseEntity = ResponseUtil.getResponse(200, "Class details updated successfully!", classEntity);
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

	@DeleteMapping("/{roomNo}")
	public ResponseEntity<Response> deleteClass(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		ClassEntity classEntity = null;
		try {
			classEntity = classService.deleteClass(roomNo);
			if (classEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Class details deleted successfully!", classEntity);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/{roomNo}")
	public ResponseEntity<Response> getParticularClass(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		ClassEntity classEntity = null;
		try {
			classEntity = classService.getParticularClass(roomNo);
			if (classEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", classEntity);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No data found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/standard/{standard}")
	public ResponseEntity<Response> getClassList(@PathVariable("standard") String standard) {
		ResponseEntity<Response> responseEntity = null;
		List<ClassEntity> classList = null;
		try {
			classList = classService.getClassList(standard);
			if (!classList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", classList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No section found for given standard!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/{standard}/{section}")
	public ResponseEntity<Response> getClassRoomNo(@PathVariable("standard") String standard,
			@PathVariable("section") String section) {
		ResponseEntity<Response> responseEntity = null;
		Long roomNo = null;
		try {
			roomNo = classService.getClassRoomNo(standard, section);
			if (roomNo != null) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", roomNo);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No class room found!");
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

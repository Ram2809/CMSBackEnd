package com.curriculum.controller;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
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

import com.curriculum.dto.HeadMaster;
import com.curriculum.entity.HeadMasterEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.HeadMasterNotFoundException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.HeadMasterService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/headmaster")
@CrossOrigin("http://localhost:4200")
public class HeadMasterController {
	@Autowired
	private HeadMasterService headMasterService;
	private Logger logger = Logger.getLogger(HeadMasterController.class);

	@PostMapping
	public ResponseEntity<Response> addHeadMaster(@Valid @RequestBody HeadMaster headMaster) {
		ResponseEntity<Response> responseEntity = null;
		Long headMasterId = null;
		try {
			headMasterId = headMasterService.addHeadMaster(headMaster);
			headMaster.setId(headMasterId);
			responseEntity = ResponseUtil.getResponse(200, "Headmaster details added successfully!", headMaster);
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

	@PutMapping("/{id}")
	public ResponseEntity<Response> updateHeadMaster(@PathVariable("id") Long id, @RequestBody HeadMaster headMaster) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		HeadMasterEntity headMasterEntity = null;
		try {
			headMasterEntity = headMasterService.updateHeadMaster(id, headMaster);
			if (headMasterEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Headmaster details updated successfully!",
						headMasterEntity);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			if (e instanceof ConstraintValidationException)
				responseEntity = ResponseUtil.getResponse(422, e.getMessage());
			else
				responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteHeadMaster(@PathVariable("id") Long id) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		HeadMasterEntity headMasterEntity = null;
		try {
			headMasterEntity = headMasterService.deleteHeadMaster(id);
			if (headMasterEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Headmaster details deleted successfully!",
						headMasterEntity);
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

	@GetMapping("/{email}")
	public ResponseEntity<Response> getHeadMaster(@PathVariable("email") String email) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		HeadMasterEntity headMasterEntity = null;
		try {
			headMasterEntity = headMasterService.getHeadMaster(email);
			responseEntity = ResponseUtil.getResponse(200, "Success!", headMasterEntity);
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

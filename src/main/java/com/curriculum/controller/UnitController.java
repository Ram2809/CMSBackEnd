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

import com.curriculum.dto.Unit;
import com.curriculum.entity.UnitEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.UnitService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/unit")
@CrossOrigin("http://localhost:4200")
public class UnitController {
	@Autowired
	private UnitService unitService;
	private Logger logger = Logger.getLogger(UnitController.class);

	@PostMapping
	public ResponseEntity<Response> addUnit(@Valid @RequestBody Unit unit) {
		ResponseEntity<Response> responseEntity = null;
		String unitNo = null;
		try {
			unitNo = unitService.addUnit(unit);
			if (unitNo.length() > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Unit details added successfully!", unit);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", unit);
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

	@GetMapping("subject/{subjectCode}")
	public ResponseEntity<Response> getUnitBySubjectCode(@PathVariable("subjectCode") String subjectCode) {
		ResponseEntity<Response> responseEntity = null;
		List<UnitEntity> unitsList = new ArrayList<>();
		try {
			unitsList = unitService.getUnitBySubjectCode(subjectCode);
			if (!unitsList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", unitsList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No units found!", unitsList);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping
	public ResponseEntity<Response> getUnitByUnitNo(@RequestParam("unitNo") String unitNo) {
		ResponseEntity<Response> responseEntity = null;
		UnitEntity unitEntity = null;
		try {
			unitEntity = unitService.getUnitByUnitNo(unitNo);
			responseEntity = ResponseUtil.getResponse(200, "Success!", unitEntity);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@PutMapping("/{unitNo}")
	public ResponseEntity<Response> updateUnit(@PathVariable("unitNo") String unitNo, @RequestBody Unit unit) {
		ResponseEntity<Response> responseEntity = null;
		UnitEntity unitEntity = null;
		try {
			unitEntity = unitService.updateUnit(unitNo, unit);
			responseEntity = ResponseUtil.getResponse(200, "Unit details updated successfully!", unitEntity);
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

	@GetMapping("/{unitNo}")
	public ResponseEntity<Response> getSubjectCode(@PathVariable("unitNo") String unitNo) {
		ResponseEntity<Response> responseEntity = null;
		String subjectCode = null;
		try {
			subjectCode = unitService.getSubjectCode(unitNo);
			responseEntity = ResponseUtil.getResponse(200, "Success!", subjectCode);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@DeleteMapping("/{unitNo}")
	public ResponseEntity<Response> deleteUnit(@PathVariable("unitNo") String unitNo) {
		ResponseEntity<Response> responseEntity = null;
		UnitEntity unitEntity = null;
		try {
			unitEntity = unitService.deleteUnit(unitNo);
			if (unitEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Unit details deleted successfully!", unitEntity);
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

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> validationFailed(MethodArgumentNotValidException e) {
		logger.error("Validation fails, Check your input!");
		ResponseEntity<Response> responseEntity = null;
		responseEntity = ResponseUtil.getResponse(422, "Validation fails!");
		return responseEntity;
	}
}

package com.curriculum.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
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

import com.curriculum.dto.UnitStatus;
import com.curriculum.entity.UnitStatusEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.UnitStatusService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/unitstatus")
@CrossOrigin("*")
public class UnitStatusController {
	@Autowired
	private UnitStatusService unitStatusService;

	@PostMapping
	public ResponseEntity<Response> addUnitStatus(@Valid @RequestBody UnitStatus unitStatus) {
		ResponseEntity<Response> responseEntity = null;
		Long statusId = null;
		try {
			statusId = unitStatusService.addUnitStatus(unitStatus);
			if (statusId > 0) {
				unitStatus.setId(statusId);
				responseEntity = ResponseUtil.getResponse(200, "Unit Status Details Added Successfully!", unitStatus);
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

	@GetMapping("/{unitNo}/{staffId}/{roomNo}")
	public ResponseEntity<Response> getStatusByUnitNo(@PathVariable("unitNo") String unitNo,
			@PathVariable("staffId") Long staffId, @PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		UnitStatusEntity topicsStatus = null;
		try {
			topicsStatus = unitStatusService.getStatusByUnitNo(unitNo, staffId, roomNo);
			if (topicsStatus != null) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", topicsStatus);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No status found!", topicsStatus);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping("/{id}")
	public ResponseEntity<Response> getUnitStatus(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		UnitStatusEntity unitStatus = null;
		try {
			unitStatus = unitStatusService.getUnitStatus(id);
			if (unitStatus != null) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", unitStatus);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No status found!", unitStatus);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@PutMapping("/{id}")
	public ResponseEntity<Response> updateUnitStatus(@PathVariable("id") Long id,
			@RequestBody UnitStatus unitStatus) {
		ResponseEntity<Response> responseEntity = null;
		UnitStatusEntity unitStatusEntity = null;
		try {
			unitStatusEntity = unitStatusService.updateUnitStatus(id, unitStatus);
			responseEntity = ResponseUtil.getResponse(200, "Unit details updated successfully!", unitStatusEntity);
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

	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteUnitStatus(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		UnitStatusEntity unitStatusEntity = null;
		try {
			unitStatusEntity = unitStatusService.deleteUnitStatus(id);
			if (unitStatusEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Unit details deleted successfully!",
						unitStatusEntity);
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
}

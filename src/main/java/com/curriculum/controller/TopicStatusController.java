package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

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

import com.curriculum.dto.Address;
import com.curriculum.dto.Topic;
import com.curriculum.dto.TopicStatus;
import com.curriculum.entity.TopicEntity;
import com.curriculum.entity.TopicStatusEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.TopicStatusService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/topicstatus")
@CrossOrigin("*")
public class TopicStatusController {
	@Autowired
	private TopicStatusService topicStatusService;

	@PostMapping
	public ResponseEntity<Response> addTopicStatus(@Valid @RequestBody TopicStatus topicStatus) {
		ResponseEntity<Response> responseEntity = null;
		Long statusId = null;
		try {
			statusId = topicStatusService.addTopicStatus(topicStatus);
			if (statusId > 0) {
				topicStatus.setId(statusId);
				responseEntity = ResponseUtil.getResponse(200, "Topic Status Details Added Successfully!", topicStatus);
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
		TopicStatusEntity topicsStatus = null;
		try {
			topicsStatus = topicStatusService.getStatusByUnitNo(unitNo, staffId, roomNo);
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
	public ResponseEntity<Response> getTopicStatus(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		TopicStatusEntity topicsStatus = null;
		try {
			topicsStatus = topicStatusService.getTopicStatus(id);
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

	@PutMapping("/{id}")
	public ResponseEntity<Response> updateTopicStatus(@PathVariable("id") Long id,
			@RequestBody TopicStatus topicStatus) {
		ResponseEntity<Response> responseEntity = null;
		TopicStatusEntity topicStatusEntity = null;
		try {
			topicStatusEntity = topicStatusService.updateTopicStatus(id, topicStatus);
			responseEntity = ResponseUtil.getResponse(200, "Topic details updated successfully!", topicStatusEntity);
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
	public ResponseEntity<Response> deleteTopicStatus(@PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		TopicStatusEntity topicStatusEntity = null;
		try {
			topicStatusEntity = topicStatusService.deleteTopicStatus(id);
			if (topicStatusEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Topic details deleted successfully!",
						topicStatusEntity);
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

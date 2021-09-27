package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.TopicService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.UnitNotFoundException;

@RestController
@RequestMapping("api/topic")
@CrossOrigin("http://localhost:4200")
public class TopicController {
	@Autowired
	private TopicService topicService;
	private Logger logger = Logger.getLogger(TopicController.class);

	@PostMapping
	public ResponseEntity<Response> addTopic(@Valid @RequestBody Topic topic) {
		ResponseEntity<Response> responseEntity = null;
		String unitNo = null;
		try {
			unitNo = topicService.addTopic(topic);
			responseEntity = ResponseUtil.getResponse(200, "Topic details added successfully!", topic);
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
	public ResponseEntity<Response> getTopicBySubjectCode(@PathVariable("subjectCode") String subjectCode) {
		ResponseEntity<Response> responseEntity = null;
		List<TopicEntity> topicsList = new ArrayList<>();
		try {
			topicsList = topicService.getTopicBySubjectCode(subjectCode);
			if (!topicsList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", topicsList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No units found!",topicsList);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@GetMapping
	public ResponseEntity<Response> getTopicByUnitNo(@RequestParam("unitNo") String unitNo) {
		ResponseEntity<Response> responseEntity = null;
		TopicEntity topicEntity = null;
		try {
			topicEntity = topicService.getTopicByUnitNo(unitNo);
			responseEntity = ResponseUtil.getResponse(200, "Success!", topicEntity);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@PutMapping("/{unitNo}")
	public ResponseEntity<Response> updateTopic(@PathVariable("unitNo") String unitNo, @RequestBody Topic topic) {
		ResponseEntity<Response> responseEntity = null;
		TopicEntity topicEntity = null;
		try {
			topicEntity = topicService.updateTopic(unitNo, topic);
			responseEntity = ResponseUtil.getResponse(200, "Topic details updated successfully!", topicEntity);
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
			subjectCode = topicService.getSubjectCode(unitNo);
			responseEntity = ResponseUtil.getResponse(200, "Success!", subjectCode);
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@DeleteMapping("/{unitNo}")
	public ResponseEntity<Response> deleteTopic(@PathVariable("unitNo") String unitNo) {
		ResponseEntity<Response> responseEntity = null;
		TopicEntity topicEntity = null;
		try {
			topicEntity = topicService.deleteTopic(unitNo);
			if (topicEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Topic details deleted successfully!", topicEntity);
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

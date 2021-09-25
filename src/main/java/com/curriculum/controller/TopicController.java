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
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.TopicService;
import com.curriculum.util.Response;
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
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		String unitNo = null;
		try {
			unitNo = topicService.addTopic(topic);
			response.setCode(200);
			response.setMessage("Topic details added successfully!");
			response.setData(topic);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (NotFoundException e) {
			if (e instanceof NotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(422);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
			}
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}

	@GetMapping("subject/{subjectCode}")
	public ResponseEntity<Response> getTopicBySubjectCode(@PathVariable("subjectCode") String subjectCode) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		List<TopicEntity> topicsList = new ArrayList();
		try {
			topicsList = topicService.getTopicBySubjectCode(subjectCode);
			if (!topicsList.isEmpty()) {
				response.setCode(200);
				response.setMessage("Success!");
				response.setData(topicsList);
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(404);
				response.setMessage("No units found!");
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof SubjectNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@GetMapping
	public ResponseEntity<Response> getTopicByUnitNo(@RequestParam("unitNo") String unitNo) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		TopicEntity topicEntity = null;
		try {
			topicEntity = topicService.getTopicByUnitNo(unitNo);
			response.setCode(200);
			response.setMessage("Success!");
			response.setData(topicEntity);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof UnitNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@PutMapping("/{unitNo}")
	public ResponseEntity<Response> updateTopic(@PathVariable("unitNo") String unitNo, @RequestBody Topic topic) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		TopicEntity topicEntity = null;
		try {
			topicEntity = topicService.updateTopic(unitNo, topic);
			response.setCode(200);
			response.setMessage("Topic details updated successfully!");
			response.setData(topicEntity);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof UnitNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else if (e instanceof SubjectNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@GetMapping("/{unitNo}")
	public ResponseEntity<Response> getSubjectCode(@PathVariable("unitNo") String unitNo) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		String subjectCode = null;
		try {
			subjectCode = topicService.getSubjectCode(unitNo);
			response.setCode(200);
			response.setMessage("Success!");
			response.setData(subjectCode);
			responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof UnitNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@DeleteMapping("/{unitNo}")
	public ResponseEntity<Response> deleteTopic(@PathVariable("unitNo") String unitNo) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		TopicEntity topicEntity = null;
		try {
			topicEntity = topicService.deleteTopic(unitNo);
			if (topicEntity != null) {
				response.setCode(200);
				response.setMessage("Topic details deleted successfully!");
				response.setData(topicEntity);
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.OK);
			} else {
				response.setCode(500);
				response.setMessage("Internal Server Error");
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof UnitNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<Response>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Response> validationFailed(MethodArgumentNotValidException e) {
		logger.error("Validation fails, Check your input!");
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		response.setCode(422);
		response.setMessage("Validation fails!");
		responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY);
		return responseEntity;
	}
}

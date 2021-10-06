package com.curriculum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.TopicService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/topic")
@CrossOrigin("*")
public class TopicController {
	@Autowired
	private TopicService topicService;
	@PostMapping
	public ResponseEntity<Response> addTopic(@RequestBody Topic topic)
	{
		ResponseEntity<Response> responseEntity = null;
		Long topicId = null;
		try {
			topicId = topicService.addTopic(topic);
			topic.setId(topicId);
			if (topicId > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Topic details added successfully!", topic);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", topic);
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
	@GetMapping("/{unitNo}")
	public ResponseEntity<Response> getTopics(@PathVariable("unitNo") String unitNo)
	{
		ResponseEntity<Response> responseEntity = null;
		List<TopicEntity> topicsList = null;
		try {
			topicsList = topicService.getTopics(unitNo);
			if (!topicsList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Success!", topicsList);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", topicsList);
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
}

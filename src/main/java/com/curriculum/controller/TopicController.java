package com.curriculum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.Topic;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.service.TopicService;

@RestController
@RequestMapping("/topic")
public class TopicController {
	@Autowired
	private TopicService topicServiceImpl;
	@PostMapping("/subject/{code}/addTopic")
	public ResponseEntity<String> addTopicDetails(@PathVariable("code") String subjectCode,@RequestBody Topic topicDetails) throws SubjectNotFoundException
	{
		return topicServiceImpl.addTopicDetails(subjectCode,topicDetails);
	}
	@GetMapping("/getBySubjectCode/{subjectCode}")
	public ResponseEntity<List<String>> getTopicDetailsBySubjectCode(@PathVariable("subjectCode") String subjectCode)
	{
		return topicServiceImpl.getTopicDetailsBySubjectCode(subjectCode);
	}
	@GetMapping("/getByUnitNo/{unitNo}")
	public ResponseEntity<Topic> getTopicDetailsByUnitNo(@PathVariable("unitNo") String unitNo)
	{
		return topicServiceImpl.getTopicDetailsByUnitNo(unitNo);
	}
}

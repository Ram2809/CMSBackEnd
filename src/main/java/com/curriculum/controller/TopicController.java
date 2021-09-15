package com.curriculum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

import com.curriculum.entity.Topic;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.UnitNotFoundException;
import com.curriculum.service.TopicService;

@RestController
@RequestMapping("/topic")
@CrossOrigin("http://localhost:4200")
public class TopicController {
	@Autowired
	private TopicService topicServiceImpl;
	@PostMapping("/subject/{code}/addTopic")
	public ResponseEntity<String> addTopicDetails(@PathVariable("code") String subjectCode,@RequestBody Topic topicDetails) throws SubjectNotFoundException
	{
		return topicServiceImpl.addTopicDetails(subjectCode,topicDetails);
	}
	@GetMapping("/getBySubjectCode/{subjectCode}")
	public ResponseEntity<List<Topic>> getTopicDetailsBySubjectCode(@PathVariable("subjectCode") String subjectCode) throws SubjectNotFoundException
	{
		return topicServiceImpl.getTopicDetailsBySubjectCode(subjectCode);
	}
	@GetMapping("/getByUnitNo/{unitNo}")
	public ResponseEntity<Topic> getTopicDetailsByUnitNo(@PathVariable("unitNo") String unitNo)
	{
		return topicServiceImpl.getTopicDetailsByUnitNo(unitNo);
	}
	@PutMapping("/subject/{subjectCode}/updateTopic/{unitNo}")
	public ResponseEntity<String> updateTopicDetails(@PathVariable("subjectCode") String subjectCode,@PathVariable("unitNo") String unitNo,@RequestBody Topic topicDetails) throws SubjectNotFoundException
	{
		return topicServiceImpl.updateTopicDetails(subjectCode,unitNo,topicDetails);
	}
	@DeleteMapping("/deleteTopic/{unitNo}")
	public ResponseEntity<String> deleteTopicDetails(@PathVariable("unitNo") String unitNo) throws UnitNotFoundException
	{
		return topicServiceImpl.deleteTopicDetails(unitNo);
	}
	//@GetMapping("")
	@ExceptionHandler(SubjectNotFoundException.class)
	public ResponseEntity<String> topicNotFound(ClassNotFoundException e)
	{
		return new ResponseEntity<String>(e.getMessage(),HttpStatus.BAD_REQUEST);
	}
}

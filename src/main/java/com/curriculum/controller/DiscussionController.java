package com.curriculum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.Discussion;
import com.curriculum.service.DiscussionService;

@RestController
@RequestMapping("/discussion")
public class DiscussionController {
	@Autowired
	private DiscussionService discussionServiceImpl;
	
	@PostMapping("topic/{unitNo}/addDiscussion")
	public ResponseEntity<String> addDiscussionDetails(@PathVariable("unitNo") String unitNo,@RequestBody Discussion discussionDetails)
	{
		return discussionServiceImpl.addDiscussionDetails(unitNo,discussionDetails);
	}
	@GetMapping("/getByUnitNo/{unitNo}")
	public ResponseEntity<List<Discussion>> getDiscussionByUnitNo(@PathVariable("unitNo") String unitNo)
	{
		return discussionServiceImpl.getDiscussionByUnitNo(unitNo);
	}
	@PutMapping("/topic/{unitNo}/updateDiscussion/{questionNo}")
	public ResponseEntity<String> updateDiscussionDetails(@PathVariable("unitNo") String unitNo,@PathVariable("questionNo") Long questionNo,@RequestBody Discussion discussionDetails)
	{
		return discussionServiceImpl.updateDiscussionDetails(unitNo,questionNo,discussionDetails);
	}
	@DeleteMapping("/deleteDiscussion/{questionNo}")
	public ResponseEntity<String> deleteDiscussionDetails(@PathVariable("questionNo") Long questionNo)
	{
		return discussionServiceImpl.deleteDiscussionDetails(questionNo);
	}
	@GetMapping("/getByQuestionNo/{questionNo}")
	public ResponseEntity<Discussion> getDiscussionByQuestionNo(@PathVariable("questionNo") Long questionNo)
	{
		return discussionServiceImpl.getDiscussionByQuestionNo(questionNo);
	}
}

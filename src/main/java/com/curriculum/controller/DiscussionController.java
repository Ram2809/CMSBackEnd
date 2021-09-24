package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.Discussion;
import com.curriculum.entity.DiscussionEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.UnitNotFoundException;
import com.curriculum.exception.QuestionNotFoundException;
import com.curriculum.service.DiscussionService;
import com.curriculum.util.Response;

@RestController
@RequestMapping("api/discussion")
public class DiscussionController {
	@Autowired
	private DiscussionService discussionService;
	
	@PostMapping
	public ResponseEntity<Response> addDiscussion(@RequestBody Discussion discussion)
	{
		System.out.println("in discussion controller");
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		Long questionNo=null;
		try {
			questionNo=discussionService.addDiscussion(discussion);
			response.setCode(200);
			response.setMessage("Discussion details added successfully!");
			discussion.setQuestionNo(questionNo);
			response.setData(discussion);
			responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			if(e instanceof UnitNotFoundException)
			{
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
			else
			{
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		System.out.println("in discussion controller");
		return responseEntity;
	}
	@GetMapping("/{unitNo}")
	public ResponseEntity<Response> getDiscussionByUnitNo(@PathVariable("unitNo") String unitNo)
	{
		Response response=new Response();
		ResponseEntity responseEntity=null;
		List<DiscussionEntity> discussionsList=new ArrayList<>();
		try {
			discussionsList=discussionService.getDiscussionByUnitNo(unitNo);
			if(!discussionsList.isEmpty())
			{
				response.setCode(200);
				response.setMessage("Success!");
				response.setData(discussionsList);
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				response.setCode(404);
				response.setMessage("No discussion found!");
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if(e instanceof UnitNotFoundException)
			{
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
			else
			{
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}
	@PutMapping("/{questionNo}")
	public ResponseEntity<Response> updateDiscussion(@PathVariable("questionNo") Long questionNo,@RequestBody Discussion discussion)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		DiscussionEntity discussionEntity=null;
		try {
			discussionEntity=discussionService.updateDiscussion(questionNo,discussion);
			response.setCode(200);
			response.setMessage("Discussion details updated successfully!");
			response.setData(discussionEntity);
			responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException |NotFoundException e) {
			if(e instanceof UnitNotFoundException || e instanceof QuestionNotFoundException)
			{
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
			else
			{
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}
	@DeleteMapping("/{questionNo}")
	public ResponseEntity<Response> deleteDiscussion(@PathVariable("questionNo") Long questionNo)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		DiscussionEntity discussionEntity=null;
		try {
			discussionEntity=discussionService.deleteDiscussion(questionNo);
			if(discussionEntity!=null) {
				response.setCode(200);
				response.setMessage("Discussion details deleted successfully!");
				response.setData(discussionEntity);
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				response.setCode(500);
				response.setMessage("Internal Server Error!");
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException | NotFoundException e) {
			if(e instanceof QuestionNotFoundException)
			{
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
			else
			{
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}
	@GetMapping
	public ResponseEntity<Response> getParticularDiscussion(@RequestParam("questionNo") Long questionNo)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		DiscussionEntity discussionEntity=null;
		try {
			discussionEntity=discussionService.getParticularDiscussion(questionNo);
			response.setCode(200);
			response.setMessage("Success!");
			response.setData(discussionEntity);
			responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			if(e instanceof QuestionNotFoundException)
			{
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
			else
			{
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity=new ResponseEntity<>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}
}

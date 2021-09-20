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

import com.curriculum.entity.Discussion;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.service.DiscussionService;
import com.curriculum.util.DateValidator;
import com.curriculum.util.Response;

@RestController
@RequestMapping("api/discussion")
public class DiscussionController {
	@Autowired
	private DiscussionService discussionServiceImpl;
	
	@PostMapping
	public ResponseEntity<Response> addDiscussion(@RequestBody Discussion discussionDetails)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		Discussion discussion=null;
		try {
			discussion=discussionServiceImpl.addDiscussion(discussionDetails);
			response.setCode(200);
			response.setMessage("Discussion details added successfully!");
			response.setData(discussion);
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	@GetMapping("/{unitNo}")
	public ResponseEntity<Response> getDiscussionByUnitNo(@PathVariable("unitNo") String unitNo)
	{
		Response response=new Response();
		ResponseEntity responseEntity=null;
		List<Discussion> discussionsList=new ArrayList<>();
		try {
			discussionsList=discussionServiceImpl.getDiscussionByUnitNo(unitNo);
			if(!discussionsList.isEmpty())
			{
				response.setCode(200);
				response.setMessage("Success!");
				response.setData(discussionsList);
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				response.setCode(404);
				response.setMessage("No discussion found!");
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	@PutMapping("/{questionNo}")
	public ResponseEntity<Response> updateDiscussion(@PathVariable("questionNo") Long questionNo,@RequestBody Discussion discussionDetails)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		Discussion discussion=null;
		try {
			discussion=discussionServiceImpl.updateDiscussion(questionNo,discussionDetails);
			response.setCode(200);
			response.setMessage("Discussion details updated successfully!");
			response.setData(discussion);
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	@DeleteMapping("/{questionNo}")
	public ResponseEntity<Response> deleteDiscussion(@PathVariable("questionNo") Long questionNo)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		Discussion discussion=null;
		try {
			discussion=discussionServiceImpl.deleteDiscussion(questionNo);
			if(discussion!=null) {
				response.setCode(200);
				response.setMessage("Discussion details deleted successfully!");
				response.setData(discussion);
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				response.setCode(500);
				response.setMessage("Internal Server Error!");
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
			}
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	@GetMapping
	public ResponseEntity<Response> getParticularDiscussion(@RequestParam("questionNo") Long questionNo)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		Discussion discussion=null;
		try {
			discussion=discussionServiceImpl.getParticularDiscussion(questionNo);
			response.setCode(200);
			response.setMessage("Success!");
			response.setData(discussion);
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
}

package com.curriculum.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.HeadMaster;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.HeadMasterNotFoundException;
import com.curriculum.service.HeadMasterService;
import com.curriculum.util.Response;
@RestController
@RequestMapping("api/headmaster")
public class HeadMasterController {
	@Autowired
	private HeadMasterService headMasterServiceImpl;
	@PostMapping
	public ResponseEntity<Response> addHeadMaster(@RequestBody HeadMaster headMasterDetails)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		HeadMaster headMaster=null;
		try {
			headMaster=headMasterServiceImpl.addHeadMaster(headMasterDetails);
			response.setCode(200);
			response.setMessage("Headmaster details added successfully!");
			response.setData(headMaster);
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(500);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return responseEntity;
	}
	@PutMapping("/{id}")
	public ResponseEntity<Response> updateHeadMaster(@PathVariable("id") Long id,@RequestBody HeadMaster headMasterDetails) throws HeadMasterNotFoundException
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		HeadMaster headMaster=null;
		try {
			headMaster=headMasterServiceImpl.updateHeadMaster(id,headMasterDetails);
			response.setCode(200);
			response.setMessage("Head master details updated successfully!");
			response.setData(headMaster);
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	@DeleteMapping("/{id}")
	public ResponseEntity<Response> deleteHeadMaster(@PathVariable("id") Long id)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		HeadMaster headMaster=null;
		try {
			headMaster=headMasterServiceImpl.deleteHeadMaster(id);
			if(headMaster!=null)
			{
				response.setCode(200);
				response.setMessage("Teacher details deleted successfully!");
				response.setData(headMaster);
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
			}
			else
			{
				response.setCode(500);
				response.setMessage("Internal Server Error!");
				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
			}
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	@GetMapping("/{id}")
	public ResponseEntity<Response> getHeadMaster(@PathVariable("id") Long id) 
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		HeadMaster headMaster=null;
		try {
			headMaster=headMasterServiceImpl.getHeadMaster(id);
			response.setCode(200);
			response.setMessage("Success!");
			response.setData(headMaster);
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
}

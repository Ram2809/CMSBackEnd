//package com.curriculum.controller;
//
//import java.util.ArrayList;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.CrossOrigin;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.ExceptionHandler;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.curriculum.entity.Subject;
//import com.curriculum.exception.BusinessServiceException;
//import com.curriculum.exception.NotFoundException;
//import com.curriculum.exception.SubjectNotFoundException;
//import com.curriculum.service.SubjectService;
//import com.curriculum.util.Response;
//
//@RestController
//@RequestMapping("api/subject")
//@CrossOrigin("http://localhost:4200")
//public class SubjectController {
//	@Autowired
//	private SubjectService subjectService;
//
//	@PostMapping
//	public ResponseEntity<Response> addSubject(@RequestBody Subject subjectDetails) throws NotFoundException {
//		Response response=new Response();
//		ResponseEntity<Response> responseEntity=null;
//		Subject subject=null;
//		try {
//			subject=subjectService.addSubject(subjectDetails);
//			response.setCode(200);
//			response.setMessage("Subject details added successfully!");
//			response.setData(subject);
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//
//	@GetMapping("/getSubjectDetails")
//	public ResponseEntity<List<Subject>> getAllSubjectDetails() {
//		return subjectService.getAllSubjectDetails();
//	}
//
//	@PutMapping("/{code}")
//	public ResponseEntity<Response> updateSubject(@PathVariable("code") String code, @RequestBody Subject subjectDetails)
//	{
//		Response response=new Response();
//		ResponseEntity responseEntity=null;
//		Subject subject=null;
//		try {
//			subject=subjectService.updateSubject(code,subjectDetails);
//			response.setCode(200);
//			response.setMessage("Subject details updated successfully!");
//			response.setData(subject);
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//
//	@DeleteMapping("/{code}")
//	public ResponseEntity<Response> deleteSubject(@PathVariable("code") String subjectCode) {
//		Response response=new Response();
//		ResponseEntity responseEntity=null;
//		Subject subject=null;
//		try {
//			subject=subjectService.deleteSubject(subjectCode);
//			if(subject!=null)
//			{
//				response.setCode(200);
//				response.setMessage("Subject details deleted successfully!");
//				response.setData(subject);
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//			}
//			else
//			{
//				response.setCode(500);
//				response.setMessage("Internal Server Error");
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//
//	@GetMapping("/{code}")
//	public ResponseEntity<Response> getParticularSubject(@PathVariable("code") String subjectCode)
//	{
//		Response response=new Response();
//		ResponseEntity<Response> responseEntity=null;
//		Subject subject=null;
//		try {
//			subject=subjectService.getParticularSubject(subjectCode);
//			if(subject!=null)
//			{
//				response.setCode(200);
//				response.setMessage("Success!");
//				response.setData(subject);
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//			}
//			else
//			{
//				response.setCode(404);
//				response.setMessage("Not Found!");
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//			}
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//	@GetMapping
//	public ResponseEntity<Response> getSubjectByClass(@RequestParam("roomNo") Long roomNo) throws ClassNotFoundException
//	{
//		Response response=new Response();
//		ResponseEntity<Response> responseEntity=null;
//		List<Subject> subjectList=new ArrayList<>();
//		try {
//			subjectList=subjectService.getSubjectByClass(roomNo);
//			if(!subjectList.isEmpty())
//			{
//				response.setCode(200);
//				response.setMessage("Success!");
//				response.setData(subjectList);
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//			}
//			else
//			{
//				response.setCode(404);
//				response.setMessage("No Subject Found!");
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//			}
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//	@GetMapping("class/{roomNo}")
//	public ResponseEntity<Response> getSubjectName(@PathVariable("roomNo") Long roomNo)
//	{
//		Response response=new Response();
//		ResponseEntity responseEntity=null;
//		List<String> subjectNames=new ArrayList();
//		try {
//			subjectNames=subjectService.getSubjectName(roomNo);
//			if(!subjectNames.isEmpty())
//			{
//				response.setCode(200);
//				response.setMessage("Success!");
//				response.setData(subjectNames);
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//			}
//			else
//			{
//				response.setCode(404);
//				response.setMessage("No Subject Name Found!");
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//			}
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//	@GetMapping("/{roomNo}/{name}")
//	public ResponseEntity<String> getSubjectCode(@PathVariable("roomNo") Long roomNo,@PathVariable("name") String name) 
//	{
//		Response response=new Response();
//		ResponseEntity responseEntity=null;
//		String code=null;
//		try {
//			code=subjectService.getSubjectCode(roomNo,name);
//			if(code!=null)
//			{
//				response.setCode(200);
//				response.setMessage("Success!");
//				response.setData(code);
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//			}
//			else
//			{
//				response.setCode(404);
//				response.setMessage("No subject code found!");
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//			}
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//	
//}

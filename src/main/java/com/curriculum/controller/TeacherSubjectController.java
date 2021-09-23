//package com.curriculum.controller;
//
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.DeleteMapping;
//import org.springframework.web.bind.annotation.PathVariable;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.PutMapping;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import com.curriculum.entity.TeacherSubject;
//import com.curriculum.exception.BusinessServiceException;
//import com.curriculum.exception.SubjectNotFoundException;
//import com.curriculum.exception.TeacherNotFoundException;
//import com.curriculum.service.TeacherSubjectService;
//import com.curriculum.util.Response;
//
//@RestController
//@RequestMapping("api/teachersubject")
//public class TeacherSubjectController {
//	@Autowired
//	private TeacherSubjectService teacherSubjectService;
//	@PostMapping
//	public ResponseEntity<Response> assignTeacherSubject(@RequestBody TeacherSubject teacherSubjectDetails) throws TeacherNotFoundException, SubjectNotFoundException
//	{
//		Response response=new Response();
//		ResponseEntity<Response> responseEntity=null;
//		TeacherSubject teacherSubject=null;
//		try {
//			teacherSubject=teacherSubjectService.assignTeacherSubject(teacherSubjectDetails);
//			response.setCode(200);
//			response.setMessage("Teacher assigned for course successfully!");
//			response.setData(teacherSubject);
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//	@PutMapping("/{id}")
//	public ResponseEntity<Response> updateTeacherSubjectAssign(@PathVariable("id") Long id,@RequestBody TeacherSubject teacherSubjectDetails) throws TeacherNotFoundException, SubjectNotFoundException
//	{
//		Response response=new Response();
//		ResponseEntity<Response> responseEntity=null;
//		TeacherSubject teacherSubject=null;
//		try {
//			teacherSubject=teacherSubjectService.updateTeacherSubjectAssign(id,teacherSubjectDetails);
//			response.setCode(200);
//			response.setMessage("Teacher assigned for course successfully!");
//			response.setData(teacherSubject);
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//	@DeleteMapping("/{id}")
//	public ResponseEntity<Response> deleteTeacherSubjectAssign(@PathVariable("id") Long id) 
//	{
//		Response response=new Response();
//		ResponseEntity<Response> responseEntity=null;
//		TeacherSubject teacherSubject=null;
//		try {
//			teacherSubject=teacherSubjectService.deleteTeacherSubjectAssign(id);
//			if(teacherSubject!=null)
//			{
//				response.setCode(200);
//				response.setMessage("Teacher is deleted from subject successfully!");
//				response.setData(teacherSubject);
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
//			}
//			else
//			{
//				response.setCode(500);
//				response.setMessage("Internal Server Error!");
//				responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.INTERNAL_SERVER_ERROR);
//			}
//		} catch (BusinessServiceException e) {
//			response.setCode(404);
//			response.setMessage(e.getMessage());
//			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
//		}
//		return responseEntity;
//	}
//}

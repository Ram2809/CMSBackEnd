package com.curriculum.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.TimeTable;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.service.TimeTableService;
import com.curriculum.util.Response;

@RestController
@RequestMapping("api/timetable")
public class TimeTableController {
	@Autowired
	private TimeTableService timeTableService;
	@PostMapping
	public ResponseEntity<Response> addTimeTable(@RequestBody TimeTable timeTableDetails)
	{
		Response response=new Response();
		ResponseEntity<Response> responseEntity=null;
		TimeTable timeTable=null;
		try {
			timeTable=timeTableService.addTimeTable(timeTableDetails);
			response.setCode(200);
			response.setMessage("Time table details added successfully!");
			response.setData(timeTable);
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.OK);
		} catch (BusinessServiceException e) {
			response.setCode(404);
			response.setMessage(e.getMessage());
			responseEntity=new ResponseEntity<Response>(response,new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return responseEntity;
	}
	@GetMapping("/getTimeTable/{roomNo}")
	public ResponseEntity<List<TimeTable>> getTimeTable(@PathVariable("roomNo") Long roomNo)
	{
		return timeTableService.getTimeTable(roomNo);
	}
}

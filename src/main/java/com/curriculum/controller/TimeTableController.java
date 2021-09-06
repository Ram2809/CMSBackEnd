package com.curriculum.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.entity.TimeTable;
import com.curriculum.service.TimeTableService;

@RestController
@RequestMapping("/timeTable")
public class TimeTableController {
	@Autowired
	private TimeTableService timeTableServiceImpl;
	@PostMapping("/class/{roomNo}/addTimeTable")
	public ResponseEntity<String> addTimeTable(@PathVariable("roomNo") Long roomNo,@RequestBody TimeTable timeTableDetails)
	{
		return timeTableServiceImpl.addTimeTable(roomNo,timeTableDetails);
	}
}

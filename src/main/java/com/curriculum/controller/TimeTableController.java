package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.TimeTable;
import com.curriculum.entity.TimeTableEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.ClassNotFoundException;
import com.curriculum.service.TimeTableService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/timetable")
public class TimeTableController {
	@Autowired
	private TimeTableService timeTableService;
	private Logger logger = Logger.getLogger(TimeTableController.class);

	@PostMapping
	public ResponseEntity<Response> addTimeTable(@Valid @RequestBody TimeTable timeTable) {
		Response response = new Response();
		ResponseEntity<Response> responseEntity = null;
		TimeTableEntity timeTableEntity = null;
		try {
			timeTableEntity = timeTableService.addTimeTable(timeTable);
			response.setCode(200);
			response.setMessage("Time table details added successfully!");
			response.setData(timeTable);
			responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.OK);
		} catch (BusinessServiceException | NotFoundException e) {
			if (e instanceof ClassNotFoundException) {
				response.setCode(404);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.NOT_FOUND);
			} else {
				response.setCode(500);
				response.setMessage(e.getMessage());
				responseEntity = new ResponseEntity<>(response, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR);
			}
		}
		return responseEntity;
	}

	@GetMapping("/{roomNo}")
	public ResponseEntity<Response> getTimeTable(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		Response response = new Response();
		List<TimeTableEntity> timeTableList = new ArrayList<>();
		try {
			timeTableList = timeTableService.getTimeTable(roomNo);
			if (!timeTableList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Timetable fetched successfully!", timeTableList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Timetable Found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@DeleteMapping("/{roomNo}")
	public ResponseEntity<Response> deleteTimeTable(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		Integer count = 0;
		try {
			count = timeTableService.deleteTimeTable(roomNo);
			if (count > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Timetable details deleted successfully!", count);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

}


package com.curriculum.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.curriculum.dto.TimeTable;
import com.curriculum.entity.TimeTableEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.service.TimeTableService;
import com.curriculum.util.Response;
import com.curriculum.util.ResponseUtil;

@RestController
@RequestMapping("api/timetable")
@CrossOrigin("http://localhost:4200")
public class TimeTableController {
	@Autowired
	private TimeTableService timeTableService;

	@PostMapping
	public ResponseEntity<Response> addTimeTable(@Valid @RequestBody TimeTable timeTable) {
		ResponseEntity<Response> responseEntity = null;
		TimeTableEntity timeTableEntity = null;
		try {
			timeTableEntity = timeTableService.addTimeTable(timeTable);
			if (timeTableEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Time table details added successfully!", timeTable);
			} else {
				responseEntity = ResponseUtil.getResponse(500, "Internal Server Error!", timeTable);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(), timeTable);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(), timeTable);
		}
		return responseEntity;
	}

	@GetMapping("/{roomNo}")
	public ResponseEntity<Response> getTimeTable(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		List<TimeTableEntity> timeTableList = new ArrayList<>();
		try {
			timeTableList = timeTableService.getTimeTable(roomNo);
			if (!timeTableList.isEmpty()) {
				responseEntity = ResponseUtil.getResponse(200, "Timetable fetched successfully!", timeTableList);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Timetable Found!",timeTableList);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(),timeTableList);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(),timeTableList);
		}
		return responseEntity;
	}

	@DeleteMapping("/{roomNo}")
	public ResponseEntity<Response> deleteTimeTable(@PathVariable("roomNo") Long roomNo) {
		ResponseEntity<Response> responseEntity = null;
		Integer noOfRowsDeleted = 0;
		try {
			noOfRowsDeleted = timeTableService.deleteTimeTable(roomNo);
			if (noOfRowsDeleted > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Timetable details deleted successfully!", noOfRowsDeleted);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(),noOfRowsDeleted);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(),noOfRowsDeleted);
		}
		return responseEntity;
	}

	@GetMapping("/{roomNo}/{day}")
	public ResponseEntity<Response> getTimeTableId(@PathVariable("roomNo") Long roomNo,
			@PathVariable("day") String day) {
		ResponseEntity<Response> responseEntity = null;
		TimeTableEntity timeTableEntity = null;
		try {
			timeTableEntity = timeTableService.getTimeTableId(roomNo, day);
			if (timeTableEntity != null) {
				responseEntity = ResponseUtil.getResponse(200, "Timetable id fetched successfully!", timeTableEntity);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Timetable id Found!",timeTableEntity);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(),timeTableEntity);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(),timeTableEntity);
		}
		return responseEntity;
	}

	@PutMapping("/{period}/{subject}/{id}")
	public ResponseEntity<Response> updatePeriod(@PathVariable("period") Integer period,
			@PathVariable("subject") String subject, @PathVariable("id") Long id, @RequestBody TimeTable timeTable) {
		ResponseEntity<Response> responseEntity = null;
		Long noOfRowsUpdated = null;
		try {
			noOfRowsUpdated = timeTableService.updatePeriod(period, subject, id);
			if (noOfRowsUpdated > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Timetable updated successfully!", noOfRowsUpdated);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Timetable Found!",noOfRowsUpdated);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(),noOfRowsUpdated);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(),noOfRowsUpdated);
		}
		return responseEntity;
	}

	@GetMapping("period/{period}/{id}")
	public ResponseEntity<Response> getPeriod(@PathVariable("period") Integer period, @PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		String subjectName = null;
		try {
			subjectName = timeTableService.getPeriod(period, id);
			if (subjectName != null) {
				responseEntity = ResponseUtil.getResponse(200, "Period fetched successfully!", subjectName);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Period Found!",subjectName);
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage(),subjectName);
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage(),subjectName);
		}
		return responseEntity;
	}

}

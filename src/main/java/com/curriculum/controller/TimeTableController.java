
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
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
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
				responseEntity = ResponseUtil.getResponse(404, "No Timetable id Found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

	@PutMapping("/{period}/{subject}/{id}")
	public ResponseEntity<Response> updatePeriod(@PathVariable("period") Integer period,
			@PathVariable("subject") String subject, @PathVariable("id") Long id, @RequestBody TimeTable timeTable) {
		ResponseEntity<Response> responseEntity = null;
		Long count = null;
		try {
			count = timeTableService.updatePeriod(period, subject, id);
			if (count > 0) {
				responseEntity = ResponseUtil.getResponse(200, "Timetable updated successfully!", count);
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

	@GetMapping("period/{period}/{id}")
	public ResponseEntity<Response> getPeriod(@PathVariable("period") Integer period, @PathVariable("id") Long id) {
		ResponseEntity<Response> responseEntity = null;
		String subjectName = null;
		try {
			subjectName = timeTableService.getPeriod(period, id);
			if (subjectName != null) {
				responseEntity = ResponseUtil.getResponse(200, "Period fetched successfully!", subjectName);
			} else {
				responseEntity = ResponseUtil.getResponse(404, "No Period Found!");
			}
		} catch (BusinessServiceException e) {
			responseEntity = ResponseUtil.getResponse(500, e.getMessage());
		} catch (NotFoundException e) {
			responseEntity = ResponseUtil.getResponse(404, e.getMessage());
		}
		return responseEntity;
	}

}

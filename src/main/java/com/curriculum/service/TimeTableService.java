package com.curriculum.service;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.TimeTable;

public interface TimeTableService {
	ResponseEntity<String> addTimeTable(Long roomNo,TimeTable timeTableDetails);
}

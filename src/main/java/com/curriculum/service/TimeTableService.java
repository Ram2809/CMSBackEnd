package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.TimeTable;

public interface TimeTableService {
	ResponseEntity<String> addTimeTable(Long roomNo,TimeTable timeTableDetails);
	ResponseEntity<List<TimeTable>> getTimeTable(Long roomNo);
}

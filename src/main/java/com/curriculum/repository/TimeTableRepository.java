package com.curriculum.repository;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.TimeTable;

public interface TimeTableRepository {
	ResponseEntity<String> addTimeTable(Long roomNo,TimeTable timeTableDetails);
}

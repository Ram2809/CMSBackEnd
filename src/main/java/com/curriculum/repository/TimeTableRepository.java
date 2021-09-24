package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.dto.TimeTable;
import com.curriculum.entity.TimeTableEntity;
import com.curriculum.exception.DatabaseException;

public interface TimeTableRepository {
	TimeTableEntity addTimeTable(TimeTable timeTable) throws DatabaseException;
	//ResponseEntity<List<TimeTable>> getTimeTable(Long roomNo);
}

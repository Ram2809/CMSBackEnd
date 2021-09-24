package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.dto.TimeTable;
import com.curriculum.entity.TimeTableEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TimeTableService {
	TimeTableEntity addTimeTable(TimeTable timeTable) throws BusinessServiceException, NotFoundException;
	//ResponseEntity<List<TimeTable>> getTimeTable(Long roomNo);
}

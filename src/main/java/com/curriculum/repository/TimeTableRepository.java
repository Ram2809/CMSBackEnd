package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.TimeTable;
import com.curriculum.entity.TimeTableEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface TimeTableRepository {
	TimeTableEntity addTimeTable(TimeTable timeTable) throws DatabaseException;

	List<TimeTableEntity> getTimeTable(Long roomNo) throws DatabaseException;

	Integer deleteTimeTable(Long roomNo) throws DatabaseException;

	TimeTableEntity getTimeTableId(Long roomNo, String day) throws DatabaseException;

	Long updatePeriod(Integer period, String subject, Long id) throws DatabaseException, NotFoundException;

	String getPeriod(Integer period, Long id) throws DatabaseException, NotFoundException;

	void checkTimetableId(Long id) throws NotFoundException;
}

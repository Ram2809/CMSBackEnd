package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.TimeTable;
import com.curriculum.entity.TimeTableEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TimeTableService {
	TimeTableEntity addTimeTable(TimeTable timeTable) throws BusinessServiceException, NotFoundException;

	List<TimeTableEntity> getTimeTable(Long roomNo) throws BusinessServiceException, NotFoundException;

	Integer deleteTimeTable(Long roomNo) throws BusinessServiceException, NotFoundException;

	TimeTableEntity getTimeTableId(Long roomNo, String day) throws BusinessServiceException, NotFoundException;

	Long updatePeriod(Integer period, String subject, Long id) throws BusinessServiceException, NotFoundException;

	String getPeriod(Integer period, Long id) throws BusinessServiceException, NotFoundException;
}

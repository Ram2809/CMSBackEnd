package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.dto.TimeTable;
import com.curriculum.entity.TimeTableEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ClassNotFoundException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.TimeTableRepository;
import com.curriculum.repository.impl.ClassRepositoryImpl;
import com.curriculum.service.TimeTableService;
@Service
public class TimeTableServiceImpl implements TimeTableService{
	@Autowired
	private TimeTableRepository timeTableRepository;
	@Autowired
	private ClassRepositoryImpl classRepository;
	@Override
	public TimeTableEntity addTimeTable(TimeTable timeTable) throws BusinessServiceException, NotFoundException {
		try {
			classRepository.checkClassRoom(timeTable.getClassRoom().getRoomNo());
			return timeTableRepository.addTimeTable(timeTable);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public List<TimeTableEntity> getTimeTable(Long roomNo) throws BusinessServiceException, NotFoundException {
		try {
			classRepository.checkClassRoom(roomNo);
			return timeTableRepository.getTimeTable(roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public Integer deleteTimeTable(Long roomNo) throws BusinessServiceException,NotFoundException {
		try {
			classRepository.checkClassRoom(roomNo);
			return timeTableRepository.deleteTimeTable(roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

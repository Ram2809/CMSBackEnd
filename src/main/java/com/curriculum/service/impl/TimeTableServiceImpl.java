package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.TimeTable;
import com.curriculum.repository.TimeTableRepository;
import com.curriculum.service.TimeTableService;
@Service
public class TimeTableServiceImpl implements TimeTableService{
	@Autowired
	private TimeTableRepository timeTableRepositoryImpl;
	@Override
	public ResponseEntity<String> addTimeTable(Long roomNo, TimeTable timeTableDetails) {
		// TODO Auto-generated method stub
		return timeTableRepositoryImpl.addTimeTable(roomNo,timeTableDetails);
	}
	@Override
	public ResponseEntity<List<TimeTable>> getTimeTable(Long roomNo) {
		// TODO Auto-generated method stub
		return timeTableRepositoryImpl.getTimeTable(roomNo);
	}

}

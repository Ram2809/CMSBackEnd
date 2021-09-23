//package com.curriculum.service.impl;
//
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.curriculum.entity.TimeTable;
//import com.curriculum.exception.BusinessServiceException;
//import com.curriculum.exception.DatabaseException;
//import com.curriculum.repository.TimeTableRepository;
//import com.curriculum.service.TimeTableService;
//@Service
//public class TimeTableServiceImpl implements TimeTableService{
//	@Autowired
//	private TimeTableRepository timeTableRepository;
//	@Override
//	public TimeTable addTimeTable(TimeTable timeTableDetails) throws BusinessServiceException {
//		try {
//			return timeTableRepository.addTimeTable(timeTableDetails);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public ResponseEntity<List<TimeTable>> getTimeTable(Long roomNo) {
//		// TODO Auto-generated method stub
//		return timeTableRepository.getTimeTable(roomNo);
//	}
//
//}

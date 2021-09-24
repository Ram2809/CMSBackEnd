package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.curriculum.entity.ClassEntity;
import com.curriculum.dto.TimeTable;
import com.curriculum.entity.TimeTableEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.repository.TimeTableRepository;
import com.curriculum.util.TimeTableMapper;
@Repository
@Transactional
public class TimeTableRepositoryImpl implements TimeTableRepository{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ClassRepositoryImpl classRepositoryImpl;
	private Logger logger=Logger.getLogger(TimeTableRepositoryImpl.class);
	@Override
	public TimeTableEntity addTimeTable(TimeTable timeTable) throws DatabaseException {
		logger.info("Adding timetable details!");
		TimeTableEntity timeTableEntity=null;
		Session session=null;
		try
		{
//			System.out.println(timeTableDetails.getClassRoom().getRoomNo());
//			System.out.println(timeTableDetails.getDay());
//			System.out.println(timeTableDetails.getPeriods());
			session=sessionFactory.getCurrentSession();
			Long count=(Long) session.save(TimeTableMapper.timeTableMapper(timeTable));
			if(count>0)
			{
				timeTableEntity=TimeTableMapper.timeTableMapper(timeTable);
				System.out.println(timeTableEntity);
				logger.info("Timetable details are added successfully!");
			}
		}
		catch(HibernateException e)
		{
			logger.error("Error while adding the timetable details!");
			throw new DatabaseException(e.getMessage());
		}
		return timeTableEntity;
	}
//	@Override
//	public ResponseEntity<List<TimeTable>> getTimeTable(Long roomNo) {
//		// TODO Auto-generated method stub
//		ResponseEntity<List<TimeTable>> response=null;
//		Session session=null;
//		List<TimeTable> timeTableList=new ArrayList<>();
//		try
//		{
//			//classRepositoryImpl.checkClassRoom(roomNo);
////			if(!RoomNoStatus)
////			{
////				throw new ClassNotFoundException("Class Not Found With"+" "+roomNo+"!");
////			}
//			session=sessionFactory.getCurrentSession();
//			Query<TimeTable> query=session.createQuery("FROM TimeTable WHERE roomNo=:roomId");
//			query.setParameter("roomId", roomNo);
//			timeTableList=query.getResultList();
//			response=new ResponseEntity<List<TimeTable>>(timeTableList,new HttpHeaders(),HttpStatus.OK);
//		}
//		catch(HibernateException e)//| ClassNotFoundException e)
//		{
//			e.printStackTrace();
//		}
//		return response;
//	}

}

package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;


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
import com.curriculum.entity.TimeTable;
import com.curriculum.repository.TimeTableRepository;
@Repository
public class TimeTableRepositoryImpl implements TimeTableRepository{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ClassRepositoryImpl classRepositoryImpl;
	@Override
	public ResponseEntity<String> addTimeTable(Long roomNo, TimeTable timeTableDetails) {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean RoomNoStatus=classRepositoryImpl.checkClassRoomNo(roomNo);
			if(!RoomNoStatus)
			{
				throw new ClassNotFoundException("Class Not Found With"+" "+roomNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			ClassEntity classRoom=new ClassEntity();
			classRoom.setRoomNo(roomNo);
			TimeTable timeTable=new TimeTable();
			timeTable.setId(timeTableDetails.getId());
			timeTable.setDay(timeTableDetails.getDay());
			timeTable.setPeriodOne(timeTableDetails.getPeriodOne());
			timeTable.setPeriodTwo(timeTableDetails.getPeriodTwo());
			timeTable.setPeriodThree(timeTableDetails.getPeriodThree());
			timeTable.setPeriodFour(timeTableDetails.getPeriodFour());
			timeTable.setPeriodFive(timeTableDetails.getPeriodFive());
			timeTable.setPeriodSix(timeTableDetails.getPeriodSix());
			timeTable.setPeriodSeven(timeTableDetails.getPeriodSeven());
			timeTable.setPeriodEight(timeTableDetails.getPeriodEight());
			timeTable.setClassRoom(classRoom);
			session.save(timeTable);
			session.getTransaction().commit();
			response=new ResponseEntity<String>("TimeTable Details Added Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | ClassNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<List<TimeTable>> getTimeTable(Long roomNo) {
		// TODO Auto-generated method stub
		ResponseEntity<List<TimeTable>> response=null;
		Session session=null;
		List<TimeTable> timeTableList=new ArrayList<>();
		try
		{
			boolean RoomNoStatus=classRepositoryImpl.checkClassRoomNo(roomNo);
			if(!RoomNoStatus)
			{
				throw new ClassNotFoundException("Class Not Found With"+" "+roomNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("FROM TimeTable WHERE roomNo=:roomId");
			query.setParameter("roomId", roomNo);
			timeTableList=query.getResultList();
			response=new ResponseEntity<List<TimeTable>>(timeTableList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | ClassNotFoundException e)
		{
			e.printStackTrace();
		}
		return response;
	}

}

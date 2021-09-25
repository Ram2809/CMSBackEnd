package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.TimeTable;
import com.curriculum.entity.TimeTableEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;
import com.curriculum.repository.TimeTableRepository;
import com.curriculum.util.TimeTableMapper;

@Repository
@Transactional
public class TimeTableRepositoryImpl implements TimeTableRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(TimeTableRepositoryImpl.class);

	@Override
	public TimeTableEntity addTimeTable(TimeTable timeTable) throws DatabaseException {
		logger.info("Adding timetable details!");
		TimeTableEntity timeTableEntity = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			checkPeriod(timeTable.getDay(), timeTable.getClassRoom().getRoomNo());
			Long count = (Long) session.save(TimeTableMapper.timeTableMapper(timeTable));
			if (count > 0) {
				timeTableEntity = TimeTableMapper.timeTableMapper(timeTable);
				System.out.println(timeTableEntity);
				logger.info("Timetable details are added successfully!");
			}
		} catch (HibernateException | NotAllowedException e) {
			logger.error("Error while adding the timetable details!");
			throw new DatabaseException(e.getMessage());
		}
		return timeTableEntity;
	}

	@Override
	public List<TimeTableEntity> getTimeTable(Long roomNo) throws DatabaseException {
		logger.info("Getting timetable details!");
		Session session = null;
		List<TimeTableEntity> timeTableList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query<TimeTableEntity> query = session.createQuery("FROM TimeTableEntity WHERE roomNo=:roomId");
			query.setParameter("roomId", roomNo);
			timeTableList = query.getResultList();
			System.out.println(timeTableList);
			logger.info("Timetable details are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching timetable details!");
			throw new DatabaseException(e.getMessage());
		}
		return timeTableList;
	}

	public void checkPeriod(String day, Long classRoomNo) throws NotAllowedException {
		Session session = sessionFactory.getCurrentSession();
		Query<TimeTableEntity> query = session
				.createQuery("FROM TimeTableEntity t WHERE t.day=:day AND t.classRoom.roomNo=:roomNo");
		query.setParameter("day", day);
		query.setParameter("roomNo", classRoomNo);
		TimeTableEntity timeTableEntity = query.uniqueResultOptional().orElse(null);
		if (timeTableEntity != null) {
			throw new NotAllowedException("Already assigned periods for" + " " + day + " " + "for class Room Number"
					+ " " + classRoomNo + "!");
		}
	}

	@Override
	public Integer deleteTimeTable(Long roomNo) throws DatabaseException {
		logger.info("Deleting timetable!");
		Session session = null;
		Integer noOfRowsDeleted = 0;
		try {
			session = sessionFactory.getCurrentSession();
			Query<Long> selectQuery = session
					.createQuery("SELECT t.id FROM TimeTableEntity t WHERE t.classRoom.roomNo=:roomNo");
			selectQuery.setParameter("roomNo", roomNo);
			List<Long> timeTableIds = selectQuery.getResultList();
			for (Long id : timeTableIds) {
				Query<Integer> deletePeriodQuery = session
						.createSQLQuery("DELETE FROM Period p WHERE p.TimeTableEntity_id=" + id);
				deletePeriodQuery.executeUpdate();
			}
			Query<Integer> deleteTimeTablequery = session
					.createQuery("DELETE FROM TimeTableEntity t WHERE t.classRoom.roomNo=:roomNo");
			deleteTimeTablequery.setParameter("roomNo", roomNo);
			noOfRowsDeleted = deleteTimeTablequery.executeUpdate();
			logger.info("Timetable Deleted successfully!");
		} catch (HibernateException e) {
			logger.error("Error while deleting the timetable!");
			throw new DatabaseException(e.getMessage());
		}
		return noOfRowsDeleted;
	}
}

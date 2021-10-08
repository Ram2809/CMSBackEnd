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
import com.curriculum.exception.NotFoundException;
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
		logger.info("Adding the timetable details...");
		TimeTableEntity timeTableEntity = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			checkPeriod(timeTable.getDay(), timeTable.getClassDetail().getRoomNo());
			Long count = (Long) session.save(TimeTableMapper.timeTableMapper(timeTable));
			if (count > 0) {
				timeTableEntity = TimeTableMapper.timeTableMapper(timeTable);
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
		logger.info("Getting the timetable details...");
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
		logger.info("Deleting the timetable details...");
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
			logger.info("Timetable details are deleted successfully!");
		} catch (HibernateException e) {
			logger.error("Error while deleting the timetable details!");
			throw new DatabaseException(e.getMessage());
		}
		return noOfRowsDeleted;
	}

	@Override
	public TimeTableEntity getTimeTableId(Long roomNo, String day) throws DatabaseException {
		logger.info("Getting the timetable id...");
		Session session = null;
		TimeTableEntity timeTableEntity = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query<TimeTableEntity> query = session
					.createQuery("FROM TimeTableEntity t WHERE t.classRoom.roomNo=:roomId AND t.day=:day");
			query.setParameter("roomId", roomNo);
			query.setParameter("day", day);
			timeTableEntity = query.uniqueResultOptional().orElse(null);
			System.out.println(timeTableEntity);
			logger.info("Timetable id is fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the timetable details!");
			throw new DatabaseException(e.getMessage());
		}
		return timeTableEntity;
	}

	@Override
	public Long updatePeriod(Integer period, String subject, Long id) throws DatabaseException, NotFoundException {
		logger.info("Updating the period details..");
		Session session = null;
		Long count = 0l;
		try {
			checkTimetableId(id);
			session = sessionFactory.getCurrentSession();
			String sqlQuery = "UPDATE Period SET periods=? WHERE TimeTableEntity_id=? AND periods_KEY=?";
			Query query = session.createSQLQuery(sqlQuery);
			query.setParameter(1, subject);
			query.setParameter(2, id);
			query.setParameter(3, period);
			count = (long) query.executeUpdate();
			logger.info("Period is updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the period details!");
			throw new DatabaseException(e.getMessage());
		}
		return count;
	}

	@Override
	public String getPeriod(Integer period, Long id) throws DatabaseException, NotFoundException {
		logger.info("Getting the period details..");
		Session session = null;
		String subjectName = "";
		try {
			checkTimetableId(id);
			session = sessionFactory.getCurrentSession();
			String sqlQuery = "SELECT periods FROM Period WHERE TimeTableEntity_id=? AND periods_KEY=?";
			Query query = session.createSQLQuery(sqlQuery);
			query.setParameter(1, id);
			query.setParameter(2, period);
			subjectName = (String) query.getSingleResult();
			logger.info("Subject name is fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while getting the period details!");
			throw new DatabaseException(e.getMessage());
		}
		return subjectName;

	}

	@Override
	public void checkTimetableId(Long id) throws NotFoundException {
		logger.info("Checking the timetable id...");
		TimeTableEntity timetableEntity = null;
		Session session = sessionFactory.getCurrentSession();
		Query<TimeTableEntity> query = session.createQuery("FROM TimeTableEntity WHERE id=:id");
		query.setParameter("id", id);
		timetableEntity = query.uniqueResultOptional().orElse(null);
		logger.info("Timetable id is checked successfully!");
		if (timetableEntity == null) {
			logger.error("Error while checking timetable id!");
			throw new NotFoundException("Timetable Not Found With" + " " + id + "!");
		}
	}
}

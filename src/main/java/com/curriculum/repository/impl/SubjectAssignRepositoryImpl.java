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

import com.curriculum.dto.ListWrapper;
import com.curriculum.dto.SubjectAssign;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.exception.AssignIdNotFoundException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.SubjectAssignRepository;
import com.curriculum.util.SubjectAssignMapper;

@Repository
@Transactional
public class SubjectAssignRepositoryImpl implements SubjectAssignRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(SubjectAssignRepositoryImpl.class);

	@Override
	public Long addSubjectAssign(SubjectAssign subjectAssign) throws DatabaseException {
		logger.info("Assigning subject for class...");
		Session session = null;
		Long id = null;
		try {
			session = sessionFactory.getCurrentSession();
			id = (Long) session.save(SubjectAssignMapper.mapSubjectAssign(subjectAssign));
			if (id > 0) {
				logger.info("Subject assigned for class successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while assigning the subject for class!");
			throw new DatabaseException(e.getMessage());
		}
		return id;
	}

	@Override
	public List<SubjectAssignEntity> getSubjects(Long roomNo) throws DatabaseException {
		logger.info("Getting subject assign details for given class...");
		Session session = null;
		List<SubjectAssignEntity> subjectList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query<SubjectAssignEntity> query = session
					.createQuery("FROM SubjectAssignEntity s WHERE s.classDetail.roomNo=:roomNo");
			query.setParameter("roomNo", roomNo);
			subjectList = query.getResultList();
			logger.info("Subject assign details are fetched sucessfully!");
		} catch (HibernateException e) {
			logger.error("Error while getting subject details for givenclass !");
			throw new DatabaseException(e.getMessage());
		}
		return subjectList;
	}

	@Override
	public Long getAssignId(Long roomNo, String subjectCode) throws DatabaseException {
		logger.info("Getting subject assign id...");
		Session session = null;
		Long assignId = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query<Long> query = session.createQuery(
					"SELECT s.id FROM SubjectAssignEntity s WHERE s.classDetail.roomNo=:roomNo AND s.subject.code=:code");
			query.setParameter("roomNo", roomNo);
			query.setParameter("code", subjectCode);
			assignId = (Long) query.getSingleResult();
			logger.info("Subject assign id is fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while getting subject assign id!");
			throw new DatabaseException(e.getMessage());
		}
		return assignId;
	}

	@Override
	public String getSubjectCode(Long id, Long roomNo) throws DatabaseException, NotFoundException {
		logger.info("Getting subject code based on subject assign id and room number...");
		Session session = null;
		String subjectCode = "";
		try {
			checkAssignId(id);
			session = sessionFactory.getCurrentSession();
			Query<String> query = session.createQuery(
					"SELECT s.subject.code FROM SubjectAssignEntity s WHERE s.id=:id AND s.classDetail.roomNo=:roomNo");
			query.setParameter("id", id);
			query.setParameter("roomNo", roomNo);
			subjectCode = query.uniqueResult();
			logger.info("Subject code is fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while getting the subject code!");
			throw new DatabaseException(e.getMessage());
		}
		return subjectCode;
	}

	public void checkAssignId(Long id) throws AssignIdNotFoundException {
		logger.info("Checking the subject assign id...");
		Session session = sessionFactory.getCurrentSession();
		Query<SubjectAssignEntity> query = session.createQuery("FROM SubjectAssignEntity WHERE id=:id");
		query.setParameter("id", id);
		SubjectAssignEntity subjectAssignEntity = query.uniqueResultOptional().orElse(null);
		logger.info("Subject assign details are fetched successfully!");
		if (subjectAssignEntity == null) {
			logger.error("Error while checking the assign id!");
			throw new AssignIdNotFoundException("Assign id is not found");
		}
	}

	@Override
	public Long getRoomNo(Long id) throws DatabaseException, NotFoundException {
		logger.info("Getting class details based on id...");
		Session session = null;
		Long roomNo = 0l;
		try {
			checkAssignId(id);
			session = sessionFactory.getCurrentSession();
			Query<Long> query = session
					.createQuery("SELECT s.classDetail.roomNo FROM SubjectAssignEntity s WHERE s.id=:id");
			query.setParameter("id", id);
			roomNo = query.uniqueResult();
			logger.info("Class details are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while getting the class details!");
			throw new DatabaseException(e.getMessage());
		}
		return roomNo;
	}

	@Override
	public Long deleteSubjectAssign(Long roomNo) throws DatabaseException {
		logger.info("Deleting subject assign details...");
		Session session = null;
		Long count = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			Query<Long> query = session
					.createQuery("DELETE FROM SubjectAssignEntity s WHERE s.classDetail.roomNo=:roomNo");
			query.setParameter("roomNo", roomNo);
			count = (long) query.executeUpdate();
			logger.info("Subject Assign details deleted successfully!");
		} catch (HibernateException e) {
			logger.error("Error while deleting the subject assign details!");
			throw new DatabaseException(e.getMessage());
		}
		return count;
	}

	@Override
	public List<Long> getRoomNoList(List<Long> assignList) throws DatabaseException {
		List<Long> roomNoList = new ArrayList<>();
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			for (int i = 0; i < assignList.size(); i++) {
				Query<Long> query = session
						.createQuery("SELECT s.classDetail.roomNo FROM SubjectAssignEntity s WHERE s.id=:id");
				query.setParameter("id", assignList.get(i));
				if (!roomNoList.contains((Long) query.uniqueResult())) {
					roomNoList.add((Long) query.uniqueResult());
				}
			}
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
		return roomNoList;
	}

	@Override
	public List<String> getSubjectCodeList(List<Long> assignList, Long roomNo) throws DatabaseException {
		List<String> subjectCodeList = new ArrayList<>();
		Session session = null;
		String subjectCode = null;
		try {
			session = sessionFactory.getCurrentSession();
			List<Long> roomNoList = getRoomNoList(assignList);
			System.out.println(roomNoList);
			for (Long roomId : roomNoList) {
				if (roomId == roomNo) {
					for (Long assignId : assignList) {
						Query<String> query = session.createQuery(
								"SELECT s.subject.code FROM SubjectAssignEntity s WHERE s.id=:id AND s.classDetail.roomNo=:roomNo");
						query.setParameter("id", assignId);
						query.setParameter("roomNo", roomNo);
						subjectCode = query.uniqueResult();
						if (subjectCode != null) {
							subjectCodeList.add(subjectCode);
						}
					}
				}
			}
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
		return subjectCodeList;
	}

}

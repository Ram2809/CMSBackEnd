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

import com.curriculum.dto.Discussion;
import com.curriculum.entity.DiscussionEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.QuestionNotFoundException;
import com.curriculum.repository.DiscussionRepository;
import com.curriculum.util.DiscussionMapper;

@Repository
@Transactional
public class DiscussionRepositoryImpl implements DiscussionRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(DiscussionRepositoryImpl.class);

	@Override
	public Long addDiscussion(Discussion discussion) throws DatabaseException {
		logger.info("Adding discussion details!");
		Session session = null;
		Long questionNo = null;
		try {
			session = sessionFactory.getCurrentSession();
			questionNo = (Long) session.save(DiscussionMapper.discussionMapper(discussion));
			if (questionNo > 0) {
				logger.info("Discussion details added successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return questionNo;
	}

	@Override
	public List<DiscussionEntity> getDiscussionByUnitNo(String unitNo, Long roomNo, Long staffId)
			throws DatabaseException {
		logger.info("Getting discussion details by Unit Number!");
		Session session = null;
		List<DiscussionEntity> discussionList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query<DiscussionEntity> query = session.createQuery(
					"FROM DiscussionEntity d WHERE d.unit.unitNo=:unitId AND d.classDetail.roomNo=:roomNo AND d.teacher.id=:teacherId");
			query.setParameter("unitId", unitNo);
			query.setParameter("roomNo", roomNo);
			query.setParameter("teacherId", staffId);
			discussionList = query.list();
			logger.info("Discussion details are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return discussionList;
	}

	public void checkQuestion(Long questionNo) throws QuestionNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<DiscussionEntity> query = session.createQuery("FROM DiscussionEntity WHERE questionNo=:questionId");
		query.setParameter("questionId", questionNo);
		DiscussionEntity discussionEntity = query.uniqueResultOptional().orElse(null);
		if (discussionEntity == null) {
			throw new QuestionNotFoundException("Question Not Found With" + " " + questionNo + "!");
		}
	}

	@Override
	public DiscussionEntity updateDiscussion(Long questionNo, Discussion discussion)
			throws DatabaseException, NotFoundException {
		logger.info("Updating the discussion details!");
		Session session = null;
		DiscussionEntity discussionEntity = null;
		try {
			checkQuestion(questionNo);
			session = sessionFactory.getCurrentSession();
			DiscussionEntity discussionDetail = new DiscussionEntity();
			discussionDetail.setQuestion(discussion.getQuestion());
			discussionDetail.setAnswer(discussion.getAnswer());
			discussionDetail.setDate(discussion.getDate());
			session.find(DiscussionEntity.class, questionNo);
			DiscussionEntity updatedDiscussionEntity = session.load(DiscussionEntity.class, questionNo);
			updatedDiscussionEntity.setQuestion(discussionDetail.getQuestion());
			updatedDiscussionEntity.setAnswer(discussionDetail.getAnswer());
			updatedDiscussionEntity.setDate(discussionDetail.getDate());
			discussionEntity = (DiscussionEntity) session.merge(updatedDiscussionEntity);
			logger.info("Discussion details are updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return discussionEntity;
	}

	@Override
	public DiscussionEntity deleteDiscussion(Long questionNo) throws DatabaseException, NotFoundException {
		logger.info("Deleting the discussion details!");
		Session session = null;
		DiscussionEntity discussionEntity = null;
		try {
			checkQuestion(questionNo);
			session = sessionFactory.getCurrentSession();
			session.find(DiscussionEntity.class, questionNo);
			DiscussionEntity discussionDetail = session.load(DiscussionEntity.class, questionNo);
			session.delete(discussionDetail);
			DiscussionEntity deletedDiscussionEntity = session.get(DiscussionEntity.class, questionNo);
			if (deletedDiscussionEntity == null) {
				discussionEntity = discussionDetail;
				logger.info("Discussion is deleted successfully!");
			} else {
				logger.error("Error while deleting the discussion details!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return discussionEntity;
	}

	@Override
	public DiscussionEntity getParticularDiscussion(Long questionNo) throws DatabaseException, NotFoundException {
		logger.info("Getting discussion detail!");
		DiscussionEntity discussionEntity = null;
		Session session = null;
		try {
			checkQuestion(questionNo);
			session = sessionFactory.getCurrentSession();
			Query<DiscussionEntity> query = session.createQuery("FROM DiscussionEntity where questionNo=:questionId");
			query.setParameter("questionId", questionNo);
			discussionEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Discussion detail is fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return discussionEntity;
	}

	@Override
	public List<DiscussionEntity> getDiscussionByRoomNo(String unitNo, Long roomNo) throws DatabaseException {
		logger.info("Getting discussion details by Unit Number!");
		Session session = null;
		List<DiscussionEntity> discussionList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query<DiscussionEntity> query = session.createQuery(
					"FROM DiscussionEntity d WHERE d.unit.unitNo=:unitId AND d.classDetail.roomNo=:roomNo");
			query.setParameter("unitId", unitNo);
			query.setParameter("roomNo", roomNo);
			discussionList = query.list();
			logger.info("Discussion details are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return discussionList;
	}

}

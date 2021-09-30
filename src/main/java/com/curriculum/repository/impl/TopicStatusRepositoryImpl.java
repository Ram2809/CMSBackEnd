package com.curriculum.repository.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.TopicStatus;
import com.curriculum.entity.TopicStatusEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.TopicStatusRepository;
import com.curriculum.util.TopicStatusMapper;

@Repository
@Transactional
public class TopicStatusRepositoryImpl implements TopicStatusRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(TopicStatusRepositoryImpl.class);

	@Override
	public Long addTopicStatus(TopicStatus topicStatus) throws DatabaseException, NotAllowedException {
		logger.info("Adding topic status...");
		Session session = null;
		Long statusId = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			TopicStatusEntity topicStatusEntity=getStatusByUnitNo(topicStatus.getTopic().getUnitNo(),topicStatus.getTeacher().getId(),topicStatus.getClassDetail().getRoomNo());
			if(topicStatusEntity==null)
			{
				statusId = (Long) session.save(TopicStatusMapper.mapTopicStatus(topicStatus));
				if (statusId > 0) {
					logger.info("Topic status added successfully!");
				}
			}
			else
			{
				throw new NotAllowedException("Already added status for particular unit!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding the topic status!");
			throw new DatabaseException(e.getMessage());
		}
		return statusId;
	}

	@Override
	public TopicStatusEntity getStatusByUnitNo(String unitNo, Long staffId, Long roomNo) throws DatabaseException {
		logger.info("Geeting topic status...");
		Session session = null;
		TopicStatusEntity topicStatusEntity = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query<TopicStatusEntity> query = session.createQuery(
					"FROM TopicStatusEntity t WHERE t.topic.unitNo=:unitNo AND t.teacher.id=:staffId AND t.classDetail.roomNo=:roomNo");
			query.setParameter("unitNo", unitNo);
			query.setParameter("staffId", staffId);
			query.setParameter("roomNo", roomNo);
			topicStatusEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Topic status fetched succeddfully!");
		} catch (HibernateException e) {
			logger.error("Error while getting topic status!");
			throw new DatabaseException(e.getMessage());
		}
		return topicStatusEntity;
	}

	@Override
	public TopicStatusEntity getTopicStatus(Long id) throws DatabaseException, NotFoundException {
		logger.info("Geeting topic status...");
		Session session = null;
		TopicStatusEntity topicStatusEntity = null;
		try {
			checkStatusId(id);
			session = sessionFactory.getCurrentSession();
			Query<TopicStatusEntity> query = session.createQuery(
					"FROM TopicStatusEntity t WHERE t.id=:id");
			query.setParameter("id", id);
			topicStatusEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Topic status fetched succeddfully!");
		} catch (HibernateException e) {
			logger.error("Error while getting topic status!");
			throw new DatabaseException(e.getMessage());
		}
		return topicStatusEntity;
	}

	@Override
	public TopicStatusEntity updateTopicStatus(Long id, TopicStatus topicStatus) throws DatabaseException, NotFoundException {
		logger.info("Updating topic status details!");
		Session session = null;
		TopicStatusEntity topicStatusEntity = null;
		try {
			checkStatusId(id);
			session = sessionFactory.getCurrentSession();
			TopicStatusEntity topicStatusDetail =new TopicStatusEntity();
			topicStatusDetail.setBeginDate(topicStatus.getBeginDate());
			topicStatusDetail.setStatus(topicStatus.getStatus());
			topicStatusDetail.setCompletedDate(topicStatus.getCompletedDate());
			topicStatusDetail.setRemarks(topicStatus.getRemarks());
			session.find(TopicStatusEntity.class, id);
			TopicStatusEntity updatedTopicStatusEntity = session.load(TopicStatusEntity.class, id);
			updatedTopicStatusEntity.setBeginDate(topicStatusDetail.getBeginDate());
			updatedTopicStatusEntity.setStatus(topicStatusDetail.getStatus());
			updatedTopicStatusEntity.setCompletedDate(topicStatusDetail.getCompletedDate());
			updatedTopicStatusEntity.setRemarks(topicStatusDetail.getRemarks());
			topicStatusEntity = (TopicStatusEntity) session.merge(updatedTopicStatusEntity);
			logger.info("Topic status details updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the topic status details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicStatusEntity;
	}

	@Override
	public TopicStatusEntity deleteTopicStatus(Long id) throws DatabaseException, NotFoundException {
		logger.info("Deleting the topic status details!");
		Session session = null;
		TopicStatusEntity topicStatusEntity = null;
		try {
			checkStatusId(id);
			session = sessionFactory.getCurrentSession();
			session.find(TopicStatusEntity.class, id);
			TopicStatusEntity topicStatusDetail = session.load(TopicStatusEntity.class, id);
			session.delete(topicStatusDetail);
			TopicStatusEntity deletedTopicStatusEntity = session.get(TopicStatusEntity.class, id);
			if (deletedTopicStatusEntity == null) {
				topicStatusEntity = topicStatusDetail;
				logger.info("Topic status is deleted successfully!");
			} else {
				logger.error("Error while deleting the topic status details!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the topic status details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicStatusEntity;
	}

	@Override
	public void checkStatusId(Long id) throws NotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<TopicStatusEntity> query = session.createQuery("FROM TopicStatusEntity WHERE id=:id");
		query.setParameter("id", id);
		TopicStatusEntity topicStatusEntity = query.uniqueResultOptional().orElse(null);
		if (topicStatusEntity == null) {
			throw new NotFoundException("Topic status not found with" + " " + id + "!");
		}
	}

}

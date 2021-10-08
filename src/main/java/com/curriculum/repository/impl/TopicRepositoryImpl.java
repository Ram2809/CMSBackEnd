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

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.TopicRepository;
import com.curriculum.util.TopicMapper;

@Repository
@Transactional
public class TopicRepositoryImpl implements TopicRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(TopicRepositoryImpl.class);

	@Override
	public Long addTopic(Topic topic) throws DatabaseException {
		logger.info("Adding the topic details...");
		Session session = null;
		Long topicNo = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			topicNo = (Long) session.save(TopicMapper.mapTopic(topic));
			if (topicNo > 0) {
				logger.info("Topic details are added successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding the topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicNo;
	}

	@Override
	public List<TopicEntity> getTopics(String unitNo) throws DatabaseException {
		logger.info("Getting the topic details...");
		Session session = null;
		List<TopicEntity> topicsList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query<TopicEntity> query = session.createQuery("FROM TopicEntity t WHERE t.unit.unitNo=:unitNo");
			query.setParameter("unitNo", unitNo);
			topicsList = query.getResultList();
			logger.info("Topic details are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicsList;
	}

	@Override
	public List<List<TopicEntity>> getTopicList(List<String> unitNoList) throws DatabaseException {
		logger.info("Getting the topic details...");
		Session session = null;
		List<List<TopicEntity>> topicsList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			for (String unitNo : unitNoList) {
				Query<TopicEntity> query = session.createQuery("FROM TopicEntity WHERE unit.unitNo=:unitNo");
				query.setParameter("unitNo", unitNo);
				List<TopicEntity> topicList = query.getResultList();
				topicsList.add(topicList);
			}
			logger.info("Topic details are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicsList;
	}

	@Override
	public TopicEntity getTopic(Long topicNo) throws DatabaseException, NotFoundException {
		logger.info("Getting topic detail for given topic number...");
		Session session = null;
		TopicEntity topicEntity = null;
		try {
			checkTopicNo(topicNo);
			session = sessionFactory.getCurrentSession();
			Query<TopicEntity> query = session.createQuery("FROM TopicEntity t WHERE t.id=:id");
			query.setParameter("id", topicNo);
			topicEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Topic detail is fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicEntity;
	}

	@Override
	public void checkTopicNo(Long topicNo) throws NotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<TopicEntity> query = session.createQuery("FROM TopicEntity WHERE id=:topicNo");
		query.setParameter("topicNo", topicNo);
		TopicEntity topicEntity = query.uniqueResultOptional().orElse(null);
		if (topicEntity == null) {
			throw new NotFoundException("Topic not found with" + " " + topicNo + "!");
		}
	}

	@Override
	public TopicEntity updateTopic(Long topicNo, Topic topic) throws DatabaseException,NotFoundException {
		logger.info("Updating the topic details...");
		Session session = null;
		TopicEntity topicEntity = null;
		try {
			checkTopicNo(topicNo);
			session = sessionFactory.getCurrentSession();
			TopicEntity topicDetail = new TopicEntity();
			topicDetail.setName(topic.getName());
			session.find(TopicEntity.class, topicNo);
			TopicEntity updatedTopicEntity = session.load(TopicEntity.class, topicNo);
			updatedTopicEntity.setName(topicDetail.getName());
			topicEntity = (TopicEntity) session.merge(updatedTopicEntity);
			logger.info("Topic details are updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicEntity;
	}

	@Override
	public TopicEntity deleteTopic(Long topicNo) throws DatabaseException, NotFoundException {
		logger.info("Deleting the topic details...");
		Session session = null;
		TopicEntity topicEntity = null;
		try {
			checkTopicNo(topicNo);
			session = sessionFactory.getCurrentSession();
			session.find(TopicEntity.class, topicNo);
			TopicEntity topicDetail = session.load(TopicEntity.class, topicNo);
			session.delete(topicDetail);
			TopicEntity deletedTopicEntity = session.get(TopicEntity.class, topicNo);
			if (deletedTopicEntity == null) {
				topicEntity = topicDetail;
				logger.info("Topic is deleted successfully!");
			} else {
				logger.error("Error while deleting the topic details!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicEntity;
	}

}

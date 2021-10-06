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
		logger.info("Adding topic details...");
		Session session=null;
		Long topicNo=0l;
		try {
			session=sessionFactory.getCurrentSession();
			topicNo=(Long) session.save(TopicMapper.mapTopic(topic));
			if(topicNo>0) {
				logger.info("Topic details added successfully!");
			}
		}
		catch(HibernateException e)
		{
			logger.error("Error while adding topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicNo;
	}

	@Override
	public List<TopicEntity> getTopics(String unitNo) throws DatabaseException {
		logger.info("Getting topic details...");
		Session session=null;
		List<TopicEntity> topicsList=new ArrayList<>();
		try {
			session=sessionFactory.getCurrentSession();
			Query<TopicEntity> query=session.createQuery("FROM TopicEntity t WHERE t.unit.unitNo=:unitNo");
			query.setParameter("unitNo", unitNo);
			topicsList=query.getResultList();
			logger.info("Topic details are fetched successfully!");
		}
		catch(HibernateException e) {
			logger.error("Error while fetching topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicsList;
	}

}

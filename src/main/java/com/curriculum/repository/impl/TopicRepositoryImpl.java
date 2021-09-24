package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.curriculum.entity.SubjectEntity;
import com.curriculum.entity.TopicEntity;
import com.curriculum.dto.Topic;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.UnitNotFoundException;
import com.curriculum.repository.TopicRepository;
import com.curriculum.util.TopicMapper;

@Repository
@Transactional
public class TopicRepositoryImpl implements TopicRepository{
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger=Logger.getLogger(TopicRepositoryImpl.class);
	@Override
	public String addTopic(Topic topic) throws DatabaseException, NotFoundException{
		logger.info("Adding the topic details!");
		Session session=null;
		String unitNo=null;
		try
		{
			session=sessionFactory.getCurrentSession();
			unitNo=(String) session.save(TopicMapper.topicMapper(topic));
			if(!unitNo.isEmpty())
			{
				logger.info("Unit is added successfully!");
			}
		}
		catch(HibernateException e)
		{
			logger.error("Error while adding the topic!");
			throw new DatabaseException(e.getMessage(),e);
		}
		return unitNo;
	}
	@Override
	public List<TopicEntity> getTopicBySubjectCode(String subjectCode) throws DatabaseException{
		logger.info("Getting units for given subject!");
		Session session=null;
		List<TopicEntity> topicsList=new ArrayList<>();
		try
		{
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("FROM TopicEntity t WHERE t.subject.code=:subjectCode");
			query.setParameter("subjectCode", subjectCode);
			topicsList=query.getResultList();
			logger.info("Unit details are fetched successfully!");
		}
		catch(HibernateException e)
		{
			logger.error("Error while fetching the unit details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicsList;
	}
	public void checkTopic(String unitNo) throws UnitNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<TopicEntity> query = session.createQuery("FROM TopicEntity WHERE unitNo=:topicNo");
		query.setParameter("topicNo",unitNo);
		TopicEntity topicEntity = query.uniqueResultOptional().orElse(null);
		if (topicEntity==null) {
			throw new UnitNotFoundException("Topic not found with"+" "+unitNo+"!");
		}
	}
	@Override
	public TopicEntity getTopicByUnitNo(String unitNo) throws DatabaseException, NotFoundException {
		logger.info("Getting particular topic details");
		Session session=null;
		TopicEntity topicEntity=null;
		try
		{
			checkTopic(unitNo);
			session=sessionFactory.getCurrentSession();
			Query<TopicEntity> query=session.createQuery("FROM TopicEntity WHERE unitNo=:topicNo");
			query.setParameter("topicNo", unitNo);
			topicEntity=query.uniqueResultOptional().orElse(null);
			logger.info("Topic details fetched successfully!");
		}
		catch(HibernateException e)
		{	
			logger.error("Error while fetching the topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicEntity;
	}
	@Override
	public TopicEntity updateTopic(String unitNo, Topic topic) throws DatabaseException, NotFoundException {
		logger.info("Updating topic details!");
		Session session=null;
		TopicEntity topicEntity=null;
		try
		{
			checkTopic(unitNo);
			session=sessionFactory.getCurrentSession();
			TopicEntity topicDetail=TopicMapper.topicMapper(topic);
			session.find(TopicEntity.class, unitNo);
			TopicEntity updatedTopicEntity=session.load(TopicEntity.class, unitNo);
			updatedTopicEntity.setUnitName(topicDetail.getUnitName());
			updatedTopicEntity.setDescription(topicDetail.getDescription());
			updatedTopicEntity.setBeginDate(topicDetail.getBeginDate());
			updatedTopicEntity.setStatus(topicDetail.getStatus());
			updatedTopicEntity.setEndDate(topicDetail.getEndDate());
			SubjectEntity subjectEntity=new SubjectEntity();
			subjectEntity.setCode(topic.getSubject().getCode());
			updatedTopicEntity.setSubject(subjectEntity);
			topicEntity=(TopicEntity) session.merge(updatedTopicEntity);
			logger.info("Topic details updated successfully!");
		}
		catch(HibernateException e)
		{
			logger.error("Error while updating the topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicEntity;
	}

	@Override
	public TopicEntity deleteTopic(String unitNo) throws DatabaseException, NotFoundException{
		logger.info("Deleting the topic details!");
		Session session=null;
		TopicEntity topicEntity=null;
		try
		{
			checkTopic(unitNo);
			session=sessionFactory.getCurrentSession();
			session.find(TopicEntity.class, unitNo);
			TopicEntity topicDetail=session.load(TopicEntity.class, unitNo);
			session.delete(topicDetail);
			TopicEntity deletedTopicEntity=session.get(TopicEntity.class, unitNo);
			if(deletedTopicEntity==null)
			{
				topicEntity=topicDetail;
				logger.info("Topic is deleted successfully!");
			}
			else
			{
				logger.error("Error while deleting the topic details!");
			}
		}
		catch(HibernateException e)
		{
			logger.error("Error while deleting the topic details!");
			throw new DatabaseException(e.getMessage());
		}
		return topicEntity;
	}
	@Override
	public String getSubjectCode(String unitNo) throws DatabaseException, NotFoundException {
		logger.info("Getting subject by unit number!");
		Session session=null;
		String subjectCode="";
		try
		{
			checkTopic(unitNo);
			session=sessionFactory.getCurrentSession();
			Query<String> query=session.createQuery("SELECT t.subject.code FROM TopicEntity t WHERE t.unitNo=:unitNo");
			query.setParameter("unitNo", unitNo);
			subjectCode=query.uniqueResult();
			logger.info("Subject code fetched successfully using unit number!");
		}
		catch(HibernateException e)
		{
			logger.error("Error while fetching the subject code!");
			throw new DatabaseException(e.getMessage());
		}
		return subjectCode;
	}


}

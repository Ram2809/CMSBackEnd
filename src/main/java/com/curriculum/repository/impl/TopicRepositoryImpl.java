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
	@Autowired
	private SubjectRepositoryImpl subjectRepositoryImpl;
	private Logger logger=Logger.getLogger(TopicRepositoryImpl.class);
	@Override
	public String addTopic(Topic topic) throws DatabaseException, NotFoundException{
		logger.info("Adding the topic details!");
		Session session=null;
		String unitNo=null;
		try
		{
//			if(topicDetails.getUnitNo().length()>8)
//			{
//				throw new ConstraintValidationException("Unit Number not exceeds the 8 characters!");
//			}
			session=sessionFactory.getCurrentSession();
			//Query query = session.createQuery("FROM Topic WHERE unitNo=:unitCode");
//			query.setParameter("unitCode", topic.getUnitNo());
//			TopicEntity topicEntity = (TopicEntity) query.uniqueResultOptional().orElse(null);
//			if(topicEntity!=null)
//			{
//				throw new UnitNotFoundException("Unit already exits with"+" "+topic.getUnitNo()+"!");
//			}
//			Subject subject=new Subject();
//			subject.setCode(topicDetails.getSubject().getCode());
//			Topic topic=new Topic();
//			topic.setUnitNo(topicDetails.getUnitNo());
//			topic.setUnitName(topicDetails.getUnitName());
//			topic.setBeginDate(topicDetails.getBeginDate());
//			topic.setStatus(topicDetails.getStatus());
//			topic.setDescription(topicDetails.getDescription());
//			topic.setEndDate(topicDetails.getEndDate());
//			topic.setSubject(subject);
			unitNo=(String) session.save(TopicMapper.topicMapper(topic));
			if(!unitNo.isEmpty())
			{
				logger.info("Unit is added successfully!");
			}
		}
		catch(HibernateException e)// |  ConstraintValidationException e)
		{
			logger.error("Error while adding the topic!");
			throw new DatabaseException(e.getMessage(),e);
		}
		return unitNo;
	}
//	@Override
//	public List<Topic> getTopicBySubjectCode(String subjectCode) throws DatabaseException{
//		logger.info("Getting units for given subject!");
//		Session session=null;
//		List<Topic> topicsList=new ArrayList<>();
//		try
//		{
//			boolean checkSubject=subjectRepositoryImpl.checkSubject(subjectCode);
//			session=sessionFactory.getCurrentSession();
//			Query query=session.createQuery("FROM Topic t WHERE t.subject.code=:subjectCode");
//			query.setParameter("subjectCode", subjectCode);
//			topicsList=query.getResultList();
//			logger.info("Unit details are fetched successfully!");
//		}
//		catch(HibernateException | SubjectNotFoundException e)
//		{
//			logger.error("Error while fetching the unit details!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return topicsList;
//	}
//	public void checkTopic(String unitNo) throws UnitNotFoundException {
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createQuery("FROM Topic WHERE unitNo=:topicNo");
//		query.setParameter("topicNo",unitNo);
//		Topic topic = (Topic) query.uniqueResultOptional().orElse(null);
//		if (topic==null) {
//			throw new UnitNotFoundException("Topic not found with"+" "+unitNo+"!");
//		}
//	}
//	@Override
//	public Topic getTopicByUnitNo(String unitNo) throws DatabaseException {
//		logger.info("Getting particular topic details");
//		Session session=null;
//		Topic topicDetails=null;
//		try
//		{
//			checkTopic(unitNo);
//			session=sessionFactory.getCurrentSession();
//			Query query=session.createQuery("FROM Topic WHERE unitNo=:topicNo");
//			query.setParameter("topicNo", unitNo);
//			topicDetails=(Topic) query.getSingleResult();
//			logger.info("Topic details fetched successfully!");
//		}
//		catch(UnitNotFoundException|HibernateException e)
//		{	
//			logger.error("Error while fetching the topic details!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return topicDetails;
//	}
//	@Override
//	public Topic updateTopic(String unitNo, Topic topicDetails) throws DatabaseException {
//		logger.info("Updating topic details!");
//		Session session=null;
//		Topic topic=null;
//		try
//		{
//			checkTopic(unitNo);
//			boolean subjectStatus=subjectRepositoryImpl.checkSubject(topicDetails.getSubject().getCode());
//			session=sessionFactory.getCurrentSession();
//			session.find(Topic.class, unitNo);
//			Topic newTopicDetails=session.load(Topic.class, unitNo);
//			Subject subjectDetails=new Subject();
//			if(topicDetails.getSubject().getCode().length()>6)
//			{
//				throw new ConstraintValidationException("Subject code must contains only 6 characters!");
//			}
//			subjectDetails.setCode(topicDetails.getSubject().getCode());
//			newTopicDetails.setUnitName(topicDetails.getUnitName());
//			newTopicDetails.setDescription(topicDetails.getDescription());
//			newTopicDetails.setBeginDate(topicDetails.getBeginDate());
//			newTopicDetails.setEndDate(topicDetails.getEndDate());
//			newTopicDetails.setStatus(topicDetails.getStatus());
//			newTopicDetails.setSubject(subjectDetails);
//			topic=(Topic) session.merge(newTopicDetails);
//			logger.info("Topic details updated successfully!");
//		}
//		catch(HibernateException | UnitNotFoundException | SubjectNotFoundException | ConstraintValidationException e)
//		{
//			logger.error("Error while updating the topic details!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return topic;
//	}
//
//	@Override
//	public Topic deleteTopic(String unitNo) throws DatabaseException{
//		logger.info("Deleting the topic details!");
//		Session session=null;
//		Topic topic=null;
//		
//		try
//		{
//			checkTopic(unitNo);
//			session=sessionFactory.getCurrentSession();
//			session.find(Topic.class, unitNo);
//			Topic topicDetails=session.load(Topic.class, unitNo);
//			session.delete(topicDetails);
//			Topic topicEntity=session.get(Topic.class, unitNo);
//			if(topicEntity==null)
//			{
//				topic=topicDetails;
//				logger.info("Topic is deleted successfully!");
//			}
//			else
//			{
//				logger.error("Error while deleting the topic details!");
//			}
//		}
//		catch(HibernateException  |UnitNotFoundException e)
//		{
//			logger.error("Error while deleting the topic details!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return topic;
//	}
//	@Override
//	public String getSubjectCode(String unitNo) throws DatabaseException {
//		logger.info("Getting subject by unit number!");
//		Session session=null;
//		String subjectCode="";
//		try
//		{
//			checkTopic(unitNo);
//			session=sessionFactory.getCurrentSession();
//			Query query=session.createQuery("SELECT t.subject.code FROM Topic t WHERE t.unitNo=:unitNo");
//			query.setParameter("unitNo", unitNo);
//			subjectCode=(String) query.uniqueResult();
//			logger.info("Subject code fetched successfully using unit number!");
//		}
//		catch(HibernateException | UnitNotFoundException e)
//		{
//			logger.error("Error while fetching the subject code!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return subjectCode;
//	}
//

}

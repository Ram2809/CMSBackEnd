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
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.curriculum.entity.Discussion;
import com.curriculum.entity.Student;
import com.curriculum.entity.Topic;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.QuestionNotFoundException;
import com.curriculum.exception.UnitNotFoundException;
import com.curriculum.repository.DiscussionRepository;
import com.curriculum.util.DateValidator;

@Repository
@Transactional
public class DiscussionRepositoryImpl implements DiscussionRepository{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TopicRepositoryImpl topicRepositoryImpl;
	private Logger logger=Logger.getLogger(DiscussionRepositoryImpl.class);
	@Override
	public Discussion addDiscussion(Discussion discussionDetails) throws DatabaseException {
		logger.info("Adding discussion details!");
		Session session=null;
		Discussion discussionEntity=null;
		try
		{
			boolean topicStatus=topicRepositoryImpl.checkTopic(discussionDetails.getTopic().getUnitNo());
			session=sessionFactory.getCurrentSession();
			Topic topic=new Topic();
			topic.setUnitNo(discussionDetails.getTopic().getUnitNo());
			Discussion discussion=new Discussion();
			discussion.setQuestionNo(discussionDetails.getQuestionNo());
			discussion.setQuestion(discussionDetails.getQuestion());
			discussion.setAnswer(discussionDetails.getAnswer());
			discussion.setDate(discussionDetails.getDate());
			discussion.setTopic(topic);
			Long count=(Long) session.save(discussion);
			if(count>0)
			{
				discussionEntity=discussion;
				logger.info("Discussion details added successfully!");
			}
		}
		catch(HibernateException | UnitNotFoundException  e)
		{
			logger.error("Error while adding discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return discussionEntity;
	}
	@Override
	public List<Discussion> getDiscussionByUnitNo(String unitNo) throws DatabaseException {
		logger.info("Getting discussion details by Unit Number!");
		Session session=null;
		List<Discussion> discussionList=new ArrayList<Discussion>();
		try
		{
			boolean topicStatus=topicRepositoryImpl.checkTopic(unitNo);
			session=sessionFactory.getCurrentSession();
			Query<Discussion> query=session.createQuery("FROM Discussion d WHERE d.topic.unitNo=:unitId");
			query.setParameter("unitId", unitNo);
			discussionList=query.list();
			logger.info("Discussion details are fetched successfully!");
		}
		catch(HibernateException |UnitNotFoundException e)
		{
			logger.error("Error while fetching the discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return discussionList;
	}
	public boolean checkQuestion(Long questionNo) throws QuestionNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Discussion WHERE questionNo=:questionId");
		query.setParameter("questionId", questionNo);
		Discussion discussion = (Discussion) query.uniqueResultOptional().orElse(null);
		if (discussion==null) {
			throw new QuestionNotFoundException("Question Not Found With"+" "+questionNo+"!");
		}
		return true;
	}
	@Override
	public Discussion updateDiscussion(Long questionNo,Discussion discussionDetails) throws DatabaseException {
		logger.info("Updating the discussion details!");
		Session session=null;
		Discussion discussionEntity=null;
		try
		{
			boolean questionStatus=checkQuestion(questionNo);
			boolean topicStatus=topicRepositoryImpl.checkTopic(discussionDetails.getTopic().getUnitNo());
			session=sessionFactory.getCurrentSession();
			session.find(Discussion.class, questionNo);
			Discussion discussion=session.load(Discussion.class, questionNo);
			Topic topic=new Topic();
			topic.setUnitNo(discussionDetails.getTopic().getUnitNo());
			discussion.setQuestion(discussionDetails.getQuestion());
			discussion.setAnswer(discussionDetails.getAnswer());
			discussion.setDate(discussionDetails.getDate());
			discussion.setTopic(topic);
			discussionEntity=(Discussion)session.merge(discussion);
			logger.info("Discussion details are updated successfully!");
		}
		catch(HibernateException | QuestionNotFoundException |UnitNotFoundException e)
		{
			logger.error("Error while updating the discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return discussionEntity;
	}
	@Override
	public Discussion deleteDiscussion(Long questionNo) throws DatabaseException {
		logger.info("Deleting the discussion details!");
		Session session=null;
		Discussion discussionDetails=null;
		try
		{
			boolean questionStatus=checkQuestion(questionNo);
			session=sessionFactory.getCurrentSession();
			session.find(Discussion.class, questionNo);
			Discussion discussion=session.load(Discussion.class, questionNo);
			session.delete(discussion);
			Discussion discussionEntity=session.get(Discussion.class, questionNo);
			if(discussionEntity==null)
			{
				discussionDetails=discussion;
				logger.info("Discussion is deleted successfully!");
			}
			else
			{
				logger.error("Error while deleting the discussion details!");
			}
		}
		catch(HibernateException | QuestionNotFoundException e)
		{
			logger.error("Error while deleting the discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return discussionDetails;
	}
	@Override
	public Discussion getParticularDiscussion(Long questionNo) throws DatabaseException {
		logger.info("Getting discussion detail!");
		Discussion discussion=null;
		Session session=null;
		try
		{
			boolean questionStatus=checkQuestion(questionNo);
			session=sessionFactory.getCurrentSession();
			Query<Discussion> query=session.createQuery("FROM Discussion where questionNo=:questionId");
			query.setParameter("questionId",questionNo);
			discussion=(Discussion) query.getSingleResult();
			logger.info("Discussion detail is fetched successfully!");
		}
		catch(HibernateException | QuestionNotFoundException e)
		{
			logger.error("Error while fetching the discussion details!");
			throw new DatabaseException(e.getMessage());
		}
		return discussion;
	}

}

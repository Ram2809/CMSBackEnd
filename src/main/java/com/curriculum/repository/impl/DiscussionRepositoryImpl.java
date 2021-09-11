package com.curriculum.repository.impl;


import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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
import com.curriculum.exception.QuestionNotFoundException;
import com.curriculum.exception.UnitNotFoundException;
import com.curriculum.repository.DiscussionRepository;

@Repository
@Transactional
public class DiscussionRepositoryImpl implements DiscussionRepository{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TopicRepositoryImpl topicRepositoryImpl;
	@Override
	public ResponseEntity<String> addDiscussionDetails(String unitNo, Discussion discussionDetails) {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean topicStatus=topicRepositoryImpl.checkTopic(unitNo);
			if(!topicStatus)
			{
				throw new UnitNotFoundException("Unit Not Found With"+" "+unitNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			Topic topic=new Topic();
			topic.setUnitNo(unitNo);
			Discussion discussion=new Discussion();
			discussion.setQuestionNo(discussionDetails.getQuestionNo());
			discussion.setQuestion(discussionDetails.getQuestion());
			discussion.setAnswer(discussionDetails.getAnswer());
			discussion.setDate(discussionDetails.getDate());
			discussion.setTopic(topic);
			session.save(discussion);
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("Discussion Details Added Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | UnitNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<List<Discussion>> getDiscussionByUnitNo(String unitNo) {
		// TODO Auto-generated method stub
		ResponseEntity<List<Discussion>> response=null;
		Session session=null;
		List<Discussion> discussionList=new ArrayList<Discussion>();
		try
		{
			boolean topicStatus=topicRepositoryImpl.checkTopic(unitNo);
			if(!topicStatus)
			{
				throw new UnitNotFoundException("Unit Not Found With"+" "+unitNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query<Discussion> query=session.createQuery("FROM Discussion d WHERE d.topic.unitNo=:unitId");
			query.setParameter("unitId", unitNo);
			discussionList=query.list();
			response=new ResponseEntity<List<Discussion>>(discussionList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException |UnitNotFoundException e)
		{
			e.printStackTrace();
		}
		return response;
	}
	public boolean checkQuestion(Long questionNo) {
		Session session = sessionFactory.getCurrentSession();
		Query<Discussion> query = session.createQuery("FROM Discussion WHERE questionNo=:questionId");
		query.setParameter("questionId", questionNo);
		List<Discussion> discussionList = query.list();
		if (discussionList.isEmpty()) {
			return false;
		}
		return true;
	}
	@Override
	public ResponseEntity<String> updateDiscussionDetails(String unitNo, Long questionNo,Discussion discussionDetails) {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean questionStatus=checkQuestion(questionNo);
			if(!questionStatus)
			{
				throw new QuestionNotFoundException("Question Not Found With"+" "+questionNo+"!");
			}
			boolean topicStatus=topicRepositoryImpl.checkTopic(unitNo);
			if(!topicStatus)
			{
				throw new UnitNotFoundException("Unit Not Found With"+" "+unitNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.find(Discussion.class, questionNo);
			Discussion discussion=session.load(Discussion.class, questionNo);
			Topic topic=new Topic();
			topic.setUnitNo(unitNo);
			discussion.setQuestion(discussionDetails.getQuestion());
			discussion.setAnswer(discussionDetails.getAnswer());
			discussion.setDate(discussionDetails.getDate());
			discussion.setTopic(topic);
			session.merge(discussion);
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("Discussion Details Updated Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | QuestionNotFoundException |UnitNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<String> deleteDiscussionDetails(Long questionNo) {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean questionStatus=checkQuestion(questionNo);
			if(!questionStatus)
			{
				throw new QuestionNotFoundException("Question Not Found With"+" "+questionNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.find(Discussion.class, questionNo);
			Discussion discussion=session.load(Discussion.class, questionNo);
			session.delete(discussion);
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("Discussion Details Deleted Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | QuestionNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<Discussion> getDiscussionByQuestionNo(Long questionNo) {
		// TODO Auto-generated method stub
		ResponseEntity<Discussion> response=null;
		Discussion discussion=new Discussion();
		Session session=null;
		try
		{
			boolean questionStatus=checkQuestion(questionNo);
			if(!questionStatus)
			{
				throw new QuestionNotFoundException("Question Not Found With"+" "+questionNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query<Discussion> query=session.createQuery("FROM Discussion where questionNo=:questionId");
			query.setParameter("questionId",questionNo);
			discussion=(Discussion) query.getSingleResult();
			response=new ResponseEntity<Discussion>(discussion,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | QuestionNotFoundException e)
		{
			e.printStackTrace();
		}
		return response;
	}

}

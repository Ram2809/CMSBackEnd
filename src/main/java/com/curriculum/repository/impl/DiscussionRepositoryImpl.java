package com.curriculum.repository.impl;


import java.util.ArrayList;
import java.util.List;

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
import com.curriculum.entity.Topic;
import com.curriculum.exception.UnitNotFoundException;
import com.curriculum.repository.DiscussionRepository;

@Repository
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
			session.beginTransaction();
			Topic topic=new Topic();
			topic.setUnitNo(unitNo);
			Discussion discussion=new Discussion();
			discussion.setQuestionNo(discussionDetails.getQuestionNo());
			discussion.setQuestion(discussionDetails.getQuestion());
			discussion.setAnswer(discussionDetails.getAnswer());
			discussion.setDate(discussionDetails.getDate());
			discussion.setTopic(topic);
			session.save(discussion);
			session.getTransaction().commit();
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
			Query query=session.createQuery("FROM Discussion d WHERE d.topic.unitNo=:unitId");
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

}

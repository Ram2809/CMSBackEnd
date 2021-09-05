package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.LogicalExpression;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Property;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.curriculum.entity.Subject;
import com.curriculum.entity.Topic;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.repository.TopicRepository;

@Repository
public class TopicRepositoryImpl implements TopicRepository{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private SubjectRepositoryImpl subjectRepositoryImpl;
	@Override
	public ResponseEntity<String> addTopicDetails(String subjectCode, Topic topicDetails) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean checkSubject=subjectRepositoryImpl.checkSubject(subjectCode);
			if(!checkSubject)
			{
				throw new SubjectNotFoundException("Subject Not Found With"+" "+subjectCode+"!");
			}
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			Subject subject=new Subject();
			subject.setCode(subjectCode);
			Topic topic=new Topic();
			topic.setUnitNo(topicDetails.getUnitNo());
			topic.setUnitName(topicDetails.getUnitName());
			topic.setBeginDate(topicDetails.getBeginDate());
			topic.setStatus(topicDetails.getStatus());
			topic.setSubject(subject);
			session.save(topic);
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Topic Added Successfully for Subject!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | SubjectNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<List<String>> getTopicDetailsBySubjectCode(String subjectCode) {
		// TODO Auto-generated method stub
		ResponseEntity<List<String>> response=null;
		Session session=null;
		List<String> topicList=new ArrayList<>();
		try
		{
			boolean checkSubject=subjectRepositoryImpl.checkSubject(subjectCode);
			if(!checkSubject)
			{
				throw new SubjectNotFoundException("Subject Not Found With"+" "+subjectCode+"!");
			}
			session=sessionFactory.getCurrentSession();
			Criteria criteria=session.createCriteria(Topic.class)
					.add(Restrictions.eq("subject.code",subjectCode))
					.setProjection(Property.forName("unitNo"));
			topicList=criteria.list();
			System.out.println(topicList);
			response=new ResponseEntity<List<String>>(topicList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | SubjectNotFoundException e)
		{
			e.printStackTrace();
		}
		
		return response;
	}

}

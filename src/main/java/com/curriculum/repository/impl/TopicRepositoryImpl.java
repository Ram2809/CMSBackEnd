package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

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

import com.curriculum.entity.Subject;
import com.curriculum.entity.Topic;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.UnitNotFoundException;
import com.curriculum.repository.TopicRepository;

@Repository
@Transactional
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
			//session.beginTransaction();
			Subject subject=new Subject();
			subject.setCode(subjectCode);
			Topic topic=new Topic();
			topic.setUnitNo(topicDetails.getUnitNo());
			topic.setUnitName(topicDetails.getUnitName());
			topic.setBeginDate(topicDetails.getBeginDate());
			topic.setStatus(topicDetails.getStatus());
			topic.setDescription(topicDetails.getDescription());
			topic.setEndDate(topicDetails.getEndDate());
			topic.setSubject(subject);
			session.save(topic);
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("Topic Added Successfully for Subject!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | SubjectNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<List<Topic>> getTopicDetailsBySubjectCode(String subjectCode) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<List<Topic>> response=null;
		Session session=null;
		List<Topic> topicDetailsList=new ArrayList<>();
		try
		{
			boolean checkSubject=subjectRepositoryImpl.checkSubject(subjectCode);
			if(!checkSubject)
			{
				throw new SubjectNotFoundException("Subject Not Found With"+" "+subjectCode+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("FROM Topic t WHERE t.subject.code=:subjectCode");
			query.setParameter("subjectCode", subjectCode);
			topicDetailsList=query.getResultList();
			response=new ResponseEntity<List<Topic>>(topicDetailsList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		
		return response;
	}
	public boolean checkTopic(String unitNo) {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Topic WHERE unitNo=:topicNo");
		query.setParameter("topicNo",unitNo);
		List<Topic> topicList = query.list();
		if (topicList.isEmpty()) {
			return false;
		}
		return true;
	}
	@Override
	public ResponseEntity<Topic> getTopicDetailsByUnitNo(String unitNo) {
		// TODO Auto-generated method stub
		ResponseEntity<Topic> response=null;
		Session session=null;
		Topic topicDetails=new Topic();
		try
		{
			boolean topicStatus=checkTopic(unitNo);
			if(!topicStatus)
			{
				throw new UnitNotFoundException("Unit Not Found With"+" "+unitNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("FROM Topic WHERE unitNo=:topicNo");
			query.setParameter("topicNo", unitNo);
			topicDetails=(Topic) query.getSingleResult();
			response=new ResponseEntity<Topic>(topicDetails,new HttpHeaders(),HttpStatus.OK);
		}
		catch(UnitNotFoundException|HibernateException e)
		{	
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public ResponseEntity<String> updateTopicDetails(String subjectCode, String unitNo, Topic topicDetails) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean topicStatus=checkTopic(unitNo);
			if(!topicStatus)
			{
				throw new UnitNotFoundException("Unit Not Found With"+" "+unitNo+"!");
			}
			boolean checkSubject=subjectRepositoryImpl.checkSubject(subjectCode);
			if(!checkSubject)
			{
				throw new SubjectNotFoundException("Subject Not Found With"+" "+subjectCode+"!");
			}
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.find(Topic.class, unitNo);
			Topic newTopicDetails=session.load(Topic.class, unitNo);
			Subject subjectDetails=new Subject();
			subjectDetails.setCode(subjectCode);
			newTopicDetails.setUnitName(topicDetails.getUnitName());
			newTopicDetails.setDescription(topicDetails.getDescription());
			newTopicDetails.setBeginDate(topicDetails.getBeginDate());
			newTopicDetails.setEndDate(topicDetails.getEndDate());
			newTopicDetails.setStatus(topicDetails.getStatus());
			newTopicDetails.setSubject(subjectDetails);
			session.merge(newTopicDetails);
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("Topic is Updated Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException |UnitNotFoundException |SubjectNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}

	@Override
	public ResponseEntity<String> deleteTopicDetails(String unitNo){// throws UnitNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean topicStatus=checkTopic(unitNo);
			if(!topicStatus)
			{
				throw new UnitNotFoundException("Unit Not Found With"+" "+unitNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			session.find(Topic.class, unitNo);
			Topic topicDetails=session.load(Topic.class, unitNo);
			session.delete(topicDetails);
			response=new ResponseEntity<String>("Topic is Deleted Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException  |UnitNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<String> getSubjectCode(String unitNo) {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		String subjectCode="";
		try
		{
			boolean topicStatus=checkTopic(unitNo);
			if(!topicStatus)
			{
				throw new UnitNotFoundException("Unit Not Found With"+" "+unitNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("SELECT t.subject.code FROM Topic t WHERE t.unitNo=:unitNo");
			query.setParameter("unitNo", unitNo);
			subjectCode=(String) query.uniqueResult();
			response=new ResponseEntity<String>(subjectCode,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | UnitNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.NOT_FOUND);
		}
		return response;
	}


}

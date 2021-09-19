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

import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.Student;
import com.curriculum.entity.Subject;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.repository.SubjectRepository;
@Repository
@Transactional
public class SubjectRepositoryImpl implements SubjectRepository{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ClassRepositoryImpl classRepositoryImpl;
	private Logger logger=Logger.getLogger(SubjectRepositoryImpl.class);
	@Override
	public Subject addSubject(Subject subjectDetails) throws DatabaseException {
		logger.info("Adding subject details!");
		Session session=null;
		Subject subject=null;
		try
		{	
			boolean checkRoomNo=classRepositoryImpl.checkClassRoom(subjectDetails.getClassRoom().getRoomNo());
			session=sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM Subject WHERE code=:subjectCode");
			query.setParameter("subjectCode", subjectDetails.getCode());
			Subject subjectDetail = (Subject) query.uniqueResultOptional().orElse(null);
			if(subjectDetail!=null)
			{
				throw new SubjectNotFoundException("Subject already exits with"+" "+subjectDetails.getCode()+"!");
			}
			if(subjectDetails.getCode().length()>6)
			{
				throw new ConstraintValidationException("Subject code must contains only 6 characters!");
			}
			ClassEntity classDetails=new ClassEntity();
			classDetails.setRoomNo(subjectDetails.getClassRoom().getRoomNo());
			Subject subjectEntity=new Subject();
			subjectEntity.setCode(subjectDetails.getCode());
			subjectEntity.setName(subjectDetails.getName());
			subjectEntity.setClassRoom(classDetails);
			String id=(String) session.save(subjectEntity);
			System.out.println(id);
			if(!id.isEmpty())
			{
				logger.info("Subject details added successfully!");
				subject=subjectEntity;
				System.out.println(subject);
			}
		}
		catch(HibernateException | ClassNotFoundException|SubjectNotFoundException|ConstraintValidationException e)
		{
			logger.error("Error while adding subject details!");
			throw new DatabaseException(e.getMessage());
		}
		return subject;
	}
	@Override
	public ResponseEntity<List<Subject>> getAllSubjectDetails() {
		// TODO Auto-generated method stub
		ResponseEntity<List<Subject>> response=null;
		Session session=null;
		List<Subject> subjectList=new ArrayList<>();
		try
		{
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("FROM Subject s");
			subjectList=query.list();
			response=new ResponseEntity<List<Subject>>(subjectList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return response;
	}
	public boolean checkSubject(String code) throws SubjectNotFoundException {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Subject WHERE code=:subjectCode");
		query.setParameter("subjectCode", code);
		Subject subject = (Subject) query.uniqueResultOptional().orElse(null);
		if (subject==null) {
			throw new SubjectNotFoundException("Subject Not Found With"+" "+code+"!");
		}
		return true;
	}
	@Override
	public ResponseEntity<String> updateSubjectDetails(String subjectCode, Long roomNo,Subject subjectDetails) throws SubjectNotFoundException,ClassNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean checkSubject=checkSubject(subjectCode);
			if(!checkSubject)
			{
				throw new SubjectNotFoundException("Subject Not Found With"+" "+subjectCode+"!");
			}
			boolean checkRoomNo=classRepositoryImpl.checkClassRoom(roomNo);
			if(!checkRoomNo)
			{
				throw new ClassNotFoundException("Class Not Found with"+" "+roomNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.find(Subject.class, subjectCode);
			Subject subjectEntity=session.load(Subject.class, subjectCode);
			ClassEntity classDetails=new ClassEntity();
			classDetails.setRoomNo(roomNo);
			subjectEntity.setName(subjectDetails.getName());
			subjectEntity.setClassRoom(classDetails);
			session.merge(subjectEntity);
			//session.flush();
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("Subject Details Updated Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<String> deleteSubjectDetails(String subjectCode) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean checkSubject=checkSubject(subjectCode);
			if(!checkSubject)
			{
				throw new SubjectNotFoundException("Subject Not Found With"+" "+subjectCode+"!");
			}
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.find(Subject.class, subjectCode);
			Subject subjectDetails=session.load(Subject.class, subjectCode);
			session.delete(subjectDetails);
			//session.flush();
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("Subject Details Deleted Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			response= new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public Subject getParticularSubject(String subjectCode) throws DatabaseException {
		logger.info("Getting subject details by code!");
		Session session=null;
		Subject subject=new Subject();
		try
		{
			boolean checkSubject=checkSubject(subjectCode);
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("FROM Subject WHERE code=:subjectCode");
			query.setParameter("subjectCode", subjectCode);
			subject=(Subject) query.getSingleResult();
			logger.info("Subject details fetched successfully!");
		}
		catch(HibernateException | SubjectNotFoundException  e)
		{
			logger.error("Error while fetching the subject details!");
			throw new DatabaseException(e.getMessage());
		}
		return subject;
	}
	@Override
	public ResponseEntity<List<Subject>> getSubjectByClass(Long roomNo) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<List<Subject>> response=null;
		Session session=null;
		List<Subject> subjectList=new ArrayList<>();
		try
		{
			boolean checkRoomNo=classRepositoryImpl.checkClassRoom(roomNo);
			if(!checkRoomNo)
			{
				throw new ClassNotFoundException("Class Not Found with"+" "+roomNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("FROM Subject WHERE roomNo=:roomId");
			query.setParameter("roomId", roomNo);
			subjectList=query.list();
			response=new ResponseEntity<List<Subject>>(subjectList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public ResponseEntity<List<String>> getSubjectName(Long roomNo) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<List<String>> response=null;
		Session session=null;
		List<String> subjectNames=new ArrayList<>();
		try
		{
			boolean checkRoomNo=classRepositoryImpl.checkClassRoom(roomNo);
			if(!checkRoomNo)
			{
				throw new ClassNotFoundException("Class Not Found with"+" "+roomNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("SELECT s.name FROM Subject s WHERE s.classRoom.roomNo=:roomId");
			query.setParameter("roomId", roomNo);
			subjectNames=query.getResultList();
			response=new ResponseEntity<List<String>>(subjectNames,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public ResponseEntity<String> getSubjectCode(Long roomNo, String name) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		String code="";
		try
		{
			boolean checkRoomNo=classRepositoryImpl.checkClassRoom(roomNo);
			if(!checkRoomNo)
			{
				throw new ClassNotFoundException("Class Not Found with"+" "+roomNo+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("SELECT s.code FROM Subject s WHERE s.name=:subjectName AND s.classRoom.roomNo=:roomId");
			query.setParameter("subjectName", name);
			query.setParameter("roomId", roomNo);
			code=(String) query.uniqueResult();
			response=new ResponseEntity<String>(code,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return response;
	}

}

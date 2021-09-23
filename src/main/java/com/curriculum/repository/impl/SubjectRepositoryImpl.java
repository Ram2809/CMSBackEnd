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

import com.curriculum.dto.Subject;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.util.SubjectMapper;
@Repository
@Transactional
public class SubjectRepositoryImpl implements SubjectRepository{
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger=Logger.getLogger(SubjectRepositoryImpl.class);
	@Override
	public String addSubject(Subject subject) throws DatabaseException {
		logger.info("Adding subject details!");
		Session session=null;
		String subjectCode=null;
		try
		{	
			session=sessionFactory.getCurrentSession();
			subjectCode=(String) session.save(SubjectMapper.subjectMapper(subject));
			if(!subjectCode.isEmpty())
			{
				logger.info("Subject details added successfully!");
			}
		}
		catch(HibernateException e)
		{
			logger.error("Error while adding subject details!");
			throw new DatabaseException(e.getMessage());
		}
		return subjectCode;
	}
//	@Override
//	public ResponseEntity<List<Subject>> getAllSubjectDetails() {
//		ResponseEntity<List<Subject>> response=null;
//		Session session=null;
//		List<Subject> subjectList=new ArrayList<>();
//		try
//		{
//			session=sessionFactory.getCurrentSession();
//			Query query=session.createQuery("FROM Subject s");
//			subjectList=query.list();
//			response=new ResponseEntity<List<Subject>>(subjectList,new HttpHeaders(),HttpStatus.OK);
//		}
//		catch(HibernateException e)
//		{
//			e.printStackTrace();
//		}
//		return response;
//	}
	public void checkSubject(String code) throws SubjectNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<SubjectEntity> query = session.createQuery("FROM SubjectEntity WHERE code=:subjectCode");
		query.setParameter("subjectCode", code);
		SubjectEntity subjectEntity = query.uniqueResultOptional().orElse(null);
		if (subjectEntity==null) {
			throw new SubjectNotFoundException("Subject Not Found With"+" "+code+"!");
		}
	}
//	@Override
//	public Subject updateSubject(String subjectCode,Subject subjectDetails) throws DatabaseException {
//		logger.info("Updating the subject details!");
//		Session session=null;
//		Subject subject=null;
//		try
//		{
//			boolean checkSubject=checkSubject(subjectCode);
//			//classRepositoryImpl.checkClassRoom(subjectDetails.getClassRoom().getRoomNo());
//			session=sessionFactory.getCurrentSession();
//			session.find(Subject.class, subjectCode);
//			Subject subjectEntity=session.load(Subject.class, subjectCode);
//			ClassEntity classDetails=new ClassEntity();
//			classDetails.setRoomNo(subjectDetails.getClassRoom().getRoomNo());
//			subjectEntity.setName(subjectDetails.getName());
//			subjectEntity.setClassRoom(classDetails);
//			subject=(Subject) session.merge(subjectEntity);
//			logger.info("Subject details updated successfully!");
//		}
//		catch(HibernateException | SubjectNotFoundException  e)
//		{
//			logger.error("Error while updating the subject!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return subject;
//	}
//	@Override
//	public Subject deleteSubject(String subjectCode) throws DatabaseException {
//		logger.info("Deleting the subject details!");
//		Session session=null;
//		Subject subject=null;
//		try
//		{
//			boolean checkSubject=checkSubject(subjectCode);
//			session=sessionFactory.getCurrentSession();
//			session.find(Subject.class, subjectCode);
//			Subject subjectDetails=session.load(Subject.class, subjectCode);
//			session.delete(subjectDetails);
//			Subject subjectEntity=session.get(Subject.class, subjectCode);
//			if(subjectEntity==null)
//			{
//				subject=subjectDetails;
//				logger.info("Subject is deleted successfully!");
//			}
//			else
//			{
//				logger.error("Error while deleting the subject!");
//			}
//		}
//		catch(HibernateException |SubjectNotFoundException e)
//		{
//			logger.error("Error while deleting the subject!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return subject;
//	}
	@Override
	public SubjectEntity getParticularSubject(String subjectCode) throws DatabaseException, NotFoundException {
		logger.info("Getting subject details by code!");
		Session session=null;
		SubjectEntity subject=null;
		try
		{
			checkSubject(subjectCode);
			session=sessionFactory.getCurrentSession();
			Query<SubjectEntity> query=session.createQuery("FROM SubjectEntity WHERE code=:subjectCode");
			query.setParameter("subjectCode", subjectCode);
			subject=query.uniqueResultOptional().orElse(null);
			logger.info("Subject details fetched successfully!");
		}
		catch(HibernateException e)
		{
			logger.error("Error while fetching the subject details!");
			throw new DatabaseException(e.getMessage());
		}
		return subject;
	}
//	@Override
//	public List<Subject> getSubjectByClass(Long roomNo) throws DatabaseException{
//		logger.info("Getting subject details by class!");
//		Session session=null;
//		List<Subject> subjectList=new ArrayList<>();
//		try
//		{
//			//classRepositoryImpl.checkClassRoom(roomNo);
//			session=sessionFactory.getCurrentSession();
//			Query query=session.createQuery("FROM Subject WHERE roomNo=:roomId");
//			query.setParameter("roomId", roomNo);
//			subjectList=query.list();
//			logger.info("Subject details are fetched successfully!");
//		}
//		catch(HibernateException e)//| ClassNotFoundException e)
//		{
//			logger.error("Error while fetching the subject details!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return subjectList;
//	}
//	@Override
//	public List<String> getSubjectName(Long roomNo) throws DatabaseException {
//		logger.info("Getting subject names!");
//		Session session=null;
//		List<String> subjectNames=new ArrayList<>();
//		try
//		{
//			//classRepositoryImpl.checkClassRoom(roomNo);
//			session=sessionFactory.getCurrentSession();
//			Query query=session.createQuery("SELECT s.name FROM Subject s WHERE s.classRoom.roomNo=:roomId");
//			query.setParameter("roomId", roomNo);
//			subjectNames=query.getResultList();
//			logger.info("Subject Names fetched successfully!");
//		}
//		catch(HibernateException e)// | ClassNotFoundException e)
//		{
//			logger.error("Error while fetching the subject names!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return subjectNames;
//	}
//	@Override
//	public String getSubjectCode(Long roomNo, String name) throws DatabaseException {
//		Session session=null;
//		String code="";
//		try
//		{
//			//classRepositoryImpl.checkClassRoom(roomNo);
//			session=sessionFactory.getCurrentSession();
//			Query query=session.createQuery("SELECT s.code FROM Subject s WHERE s.name=:subjectName AND s.classRoom.roomNo=:roomId");
//			query.setParameter("subjectName", name);
//			query.setParameter("roomId", roomNo);
//			code=(String) query.uniqueResult();
//		}
//		catch(HibernateException e)//|ClassNotFoundException e)
//		{
//			throw new DatabaseException(e.getMessage());
//		}
//		return code;
//	}

}

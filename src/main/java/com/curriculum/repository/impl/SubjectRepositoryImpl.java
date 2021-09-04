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

import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.Student;
import com.curriculum.entity.Subject;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.repository.SubjectRepository;
@Repository
public class SubjectRepositoryImpl implements SubjectRepository{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private ClassRepositoryImpl classRepositoryImpl;
	@Override
	public ResponseEntity<String> addSubjectDetails(Long roomNo, Subject subjectDetails) throws ClassNotFoundException{
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{	
			boolean checkRoomNo=classRepositoryImpl.checkClassRoomNo(roomNo);
			if(!checkRoomNo)
			{
				throw new ClassNotFoundException("Class Not Found with"+" "+roomNo+"!");
			}
			session=sessionFactory.openSession();
			session.beginTransaction();
			ClassEntity classDetails=new ClassEntity();
			classDetails.setRoomNo(roomNo);
			Subject subjectEntity=new Subject();
			subjectEntity.setCode(subjectDetails.getCode());
			subjectEntity.setName(subjectDetails.getName());
			subjectEntity.setClassRoom(classDetails);
			session.save(subjectEntity);
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Subject Details Added Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException |ClassNotFoundException e)
		{
			response=new ResponseEntity<String>("",new HttpHeaders(),HttpStatus.OK);
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return response;
	}
	@Override
	public ResponseEntity<List<Subject>> getAllSubjectDetails() {
		// TODO Auto-generated method stub
		ResponseEntity<List<Subject>> response=null;
		Session session=null;
		List<Subject> subjectList=new ArrayList<>();
		try
		{
			session=sessionFactory.openSession();
			Query query=session.createQuery("FROM Subject s");
			subjectList=query.list();
			response=new ResponseEntity<List<Subject>>(subjectList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return response;
	}
	public boolean checkSubject(String code) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Subject WHERE code=:subjectCode");
		query.setParameter("subjectCode", code);
		List<Subject> subjectList = query.list();
		if (subjectList.isEmpty()) {
			return false;
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
			boolean checkRoomNo=classRepositoryImpl.checkClassRoomNo(roomNo);
			if(!checkRoomNo)
			{
				throw new ClassNotFoundException("Class Not Found with"+" "+roomNo+"!");
			}
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.find(Subject.class, subjectCode);
			Subject subjectEntity=session.load(Subject.class, subjectCode);
			ClassEntity classDetails=new ClassEntity();
			classDetails.setRoomNo(roomNo);
			subjectEntity.setName(subjectDetails.getName());
			subjectEntity.setClassRoom(classDetails);
			session.merge(subjectEntity);
			session.flush();
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Subject Details Updated Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | SubjectNotFoundException|ClassNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
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
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.find(Subject.class, subjectCode);
			Subject subjectDetails=session.load(Subject.class, subjectCode);
			session.delete(subjectDetails);
			session.flush();
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Subject Details Deleted Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			response= new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return response;
	}
	@Override
	public ResponseEntity<Subject> getParticularSubjectDetails(String subjectCode) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<Subject> response=null;
		Session session=null;
		Subject subjectDetails=new Subject();
		try
		{
			boolean checkSubject=checkSubject(subjectCode);
			if(!checkSubject)
			{
				throw new SubjectNotFoundException("Subject Not Found With"+" "+subjectCode+"!");
			}
			session=sessionFactory.openSession();
			Query query=session.createQuery("FROM Subject WHERE code=:subjectCode");
			query.setParameter("subjectCode", subjectCode);
			subjectDetails=(Subject) query.getSingleResult();
			response=new ResponseEntity<Subject>(subjectDetails,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException |SubjectNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return response;
	}

}

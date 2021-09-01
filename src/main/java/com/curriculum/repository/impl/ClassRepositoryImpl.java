package com.curriculum.repository.impl;

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

import com.curriculum.entity.ClassEntity;
import com.curriculum.repository.ClassRepository;

@Repository
@Transactional
public class ClassRepositoryImpl implements ClassRepository {
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public ResponseEntity<String> addClassDetails(ClassEntity classDetails) {
		Session session=null;
		ResponseEntity<String> response=null;
		try {
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.save(classDetails);
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Class Details Added Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			return new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
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
	public ResponseEntity<List<ClassEntity>> getAllClassDetails() {
		// TODO Auto-generated method stub
		ResponseEntity<List<ClassEntity>> response=null;
		Session session=null;
		try {
		session=sessionFactory.openSession();
		Query query=session.createQuery("FROM ClassEntity c");
		List<ClassEntity> classDetails=query.list();
		response=new ResponseEntity<List<ClassEntity>>(classDetails,new HttpHeaders(),HttpStatus.OK);
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
	@Override
	public ResponseEntity<String> updateClassDetails(Long roomNo, ClassEntity classDetails) {
		// TODO Auto-generated method stub
		Session session=null;
		ResponseEntity<String> response=null;
		try
		{
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.find(ClassEntity.class, roomNo);
			ClassEntity classEntity=session.load(ClassEntity.class, roomNo);
			classEntity.setStandard(classDetails.getStandard());
			classEntity.setSection(classDetails.getSection());
			session.merge(classEntity);
			session.flush();
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Class Details Updated Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			return new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
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
//	public static boolean checkClassRoomNo(Long roomNo)
//	{
//		Session session=sessionFactory.openSession();
//		Query query=session.createQuery("FROM ClassEntity c WHERE c.roomNo=:roomNo");
//		List<ClassEntity> classList=query.list();
//		if(classList.isEmpty())
//		{
//			return false;
//		}
//		return true;
//	}
}

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
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.save(classDetails);
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("Class Details Added Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			return new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<List<ClassEntity>> getAllClassDetails() {
		// TODO Auto-generated method stub
		ResponseEntity<List<ClassEntity>> response=null;
		Session session=null;
		try {
		session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("FROM ClassEntity c");
		List<ClassEntity> classDetails=query.list();
		response=new ResponseEntity<List<ClassEntity>>(classDetails,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
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
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.find(ClassEntity.class, roomNo);
			ClassEntity classEntity=session.load(ClassEntity.class, roomNo);
			classEntity.setStandard(classDetails.getStandard());
			classEntity.setSection(classDetails.getSection());
			session.merge(classEntity);
			session.flush();
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("Class Details Updated Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			return new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	public boolean checkClassRoomNo(Long roomNo)
	{
		Session session=sessionFactory.getCurrentSession();
		Query query=session.createQuery("FROM ClassEntity WHERE roomNo=:roomNo");
		query.setParameter("roomNo",roomNo);
		List<ClassEntity> classList=query.list();
		if(classList.isEmpty())
		{
			return false;
		}
		return true;
	}
	@Override
	public ResponseEntity<String> deleteClassDetails(Long roomNo) {
		// TODO Auto-generated method stub
		Session session=null;
		ResponseEntity<String> response=null;
		try
		{
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.get(ClassEntity.class, roomNo);
			ClassEntity classEntity=session.load(ClassEntity.class, roomNo);
			session.delete(classEntity);
			session.flush();
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("Class Details Deleted Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<List<ClassEntity>> getParticularClassDetails(Long roomNo) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<List<ClassEntity>> response=null;
		Session session=null;
		List<ClassEntity> classDetailsList=new ArrayList<>();
		ClassEntity classDetails=new ClassEntity();
		try
		{
			boolean roomNoStatus=checkClassRoomNo(roomNo);
			if(!roomNoStatus)
			{
				throw new ClassNotFoundException("Class Room Not Found with"+" "+roomNo);
			}
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("FROM ClassEntity WHERE roomNo=:roomId");
			query.setParameter("roomId", roomNo);
			classDetailsList=query.list();
			classDetails=(ClassEntity) query.getSingleResult();
			System.out.println(classDetails.getRoomNo());
			System.out.println(classDetails.getStandard());
			System.out.println(classDetails.getSection());
			response=new ResponseEntity<List<ClassEntity>>(classDetailsList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return response;
	}
	//@SuppressWarnings("unchecked")
	@Override
	public ResponseEntity<List<String>> getSection(String standard) {
		// TODO Auto-generated method stub
		ResponseEntity<List<String>> response=null;
		Session session=null;
		List<String> sectionList=new ArrayList<>();
		try
		{
			session=sessionFactory.getCurrentSession();
//			CriteriaBuilder criteria=session.createCriteria(ClassEntity.class)
//					.add(Restrictions.eq("standard", standard))
//					.setProjection(Property.forName("section"));
//			sectionList=criteria.list();
			Query query=session.createQuery("SELECT c.section FROM ClassEntity c where c.standard=:standard");
			query.setParameter("standard", standard);
			sectionList=query.list();
			response=new ResponseEntity<List<String>>(sectionList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public ResponseEntity<Long> getClassDetails(String standard, String section) {
		// TODO Auto-generated method stub
		ResponseEntity<Long> response=null;
		Session session=null;
		Long classList=null;
		try
		{
			session=sessionFactory.getCurrentSession();
//			Criteria criteria=session.createCriteria(ClassEntity.class);
//			Criterion standardCriteria=Restrictions.eq("standard", standard);
//			Criterion sectionCriteria=Restrictions.eq("section",section);
//			LogicalExpression andExp=Restrictions.and(standardCriteria,sectionCriteria);
//			criteria.add(andExp)
//			.setProjection(Property.forName("roomNo"));
//			classList=(Long) criteria.uniqueResult();
			Query query=session.createQuery("SELECT c.roomNo FROM ClassEntity c WHERE c.standard=:standard AND c.section=:section");
			query.setParameter("standard", standard);
			query.setParameter("section", section);
			classList=(Long) query.uniqueResult();
			response=new ResponseEntity<Long>(classList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		return response;
	}
}

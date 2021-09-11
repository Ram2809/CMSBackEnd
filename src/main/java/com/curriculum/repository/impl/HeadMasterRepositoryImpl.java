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

import com.curriculum.entity.HeadMaster;
import com.curriculum.exception.HeadMasterNotFoundException;
import com.curriculum.repository.HeadMasterRepository;
@Repository
@Transactional
public class HeadMasterRepositoryImpl implements HeadMasterRepository{
	@Autowired
	private SessionFactory sessionFactory;
	public ResponseEntity<String> addHeadMasterDetails(HeadMaster headMasterDeteails) {
		// TODO Auto-generated method stub
		Session session=null;
		ResponseEntity<String> response=null;
		try
		{
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.save(headMasterDeteails);
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("HeadMaster Details Added Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	
	public boolean checkHeadMaster(Long id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM HeadMaster WHERE id=:headMasterId");
		query.setParameter("headMasterId", id);
		List<HeadMaster> headMasterList = query.list();
		if (headMasterList.isEmpty()) {
			return false;
		}
		return true;
	}
	public ResponseEntity<String> updateHeadMasterDetails(Long id, HeadMaster headMasterDetails) throws HeadMasterNotFoundException {
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean checkHeadMaster=checkHeadMaster(id);
			if(!checkHeadMaster)
			{
				throw new HeadMasterNotFoundException("HeadMaster Not Found with"+" "+id+"!");
			}
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.find(HeadMaster.class, id);
			HeadMaster newHeadMasterDetails=session.load(HeadMaster.class, id);
			newHeadMasterDetails.setFirstName(headMasterDetails.getFirstName());
			newHeadMasterDetails.setLastName(headMasterDetails.getLastName());
			newHeadMasterDetails.setDateOfBirth(headMasterDetails.getDateOfBirth());
			newHeadMasterDetails.setGender(headMasterDetails.getGender());
			newHeadMasterDetails.setQualification(headMasterDetails.getQualification());
			newHeadMasterDetails.setEmail(headMasterDetails.getEmail());
			newHeadMasterDetails.setContactNo(headMasterDetails.getContactNo());
			newHeadMasterDetails.setAddress(headMasterDetails.getAddress());
			session.merge(newHeadMasterDetails);
			session.flush();
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("HeadMaster Details Updated Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException |HeadMasterNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	public ResponseEntity<String> deleteHeadMasterDetails(Long id) {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean checkHeadMaster=checkHeadMaster(id);
			if(!checkHeadMaster)
			{
				throw new HeadMasterNotFoundException("HeadMaster Not Found with"+" "+id+"!");
			}
			session=sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.find(HeadMaster.class, id);
			HeadMaster headMasterDetails=session.load(HeadMaster.class, id);
			session.delete(headMasterDetails);
			//session.flush();
			//session.getTransaction().commit();
			response=new ResponseEntity<String>("HeadMaster Details Deleted Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException |HeadMasterNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	public ResponseEntity<HeadMaster> getParticularHeadMasterDetails(Long id)throws HeadMasterNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<HeadMaster> response=null;
		Session session=null;
		HeadMaster headMasterDetails=new HeadMaster();
		try
		{
			boolean checkHeadMaster=checkHeadMaster(id);
			if(!checkHeadMaster)
			{
				throw new HeadMasterNotFoundException("HeadMaster Not Found with"+" "+id+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query<HeadMaster> query=session.createQuery("FROM HeadMaster WHERE id=:headMasterId");
			query.setParameter("headMasterId", id);
			headMasterDetails=(HeadMaster) query.getSingleResult();
			response=new ResponseEntity<HeadMaster>(headMasterDetails,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | HeadMasterNotFoundException e)
		{
			e.printStackTrace();
		}
		return response;
	}

}

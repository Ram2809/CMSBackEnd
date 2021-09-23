//package com.curriculum.repository.impl;
//
//import javax.transaction.Transactional;
//
//import org.apache.log4j.Logger;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import com.curriculum.entity.HeadMaster;
//import com.curriculum.exception.ConstraintValidationException;
//import com.curriculum.exception.DatabaseException;
//import com.curriculum.exception.HeadMasterNotFoundException;
//import com.curriculum.repository.HeadMasterRepository;
//@Repository
//@Transactional
//public class HeadMasterRepositoryImpl implements HeadMasterRepository{
//	@Autowired
//	private SessionFactory sessionFactory;
//	private Logger logger=Logger.getLogger(HeadMasterRepositoryImpl.class);
//	public HeadMaster addHeadMaster(HeadMaster headMasterDetails) throws DatabaseException {
//		logger.info("Adding the headmaster details!");
//		Session session=null;
//		HeadMaster headMaster=null;
//		try
//		{
//			if(String.valueOf(headMasterDetails.getContactNo()).length()!=10)
//			{
//				throw new ConstraintValidationException("Phone Number must contain 10 digits!");
//			}
//			session=sessionFactory.getCurrentSession();
//			System.out.println(headMasterDetails.getContactNo());
//			Long count=(Long) session.save(headMasterDetails);
//			if(count>0)
//			{
//				headMaster=headMasterDetails;
//				logger.info("Headmaster details added successfully!");
//			}
//		}
//		catch(HibernateException | ConstraintValidationException e)
//		{
//			logger.error("Error while adding the headmaster!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return headMaster;
//	}
//	
//	public boolean checkHeadMaster(Long id) throws HeadMasterNotFoundException {
//		Session session = sessionFactory.getCurrentSession();
//		Query query = session.createQuery("FROM HeadMaster WHERE id=:headMasterId");
//		query.setParameter("headMasterId", id);
//		HeadMaster headMaster = (HeadMaster) query.uniqueResultOptional().orElse(null);
//		if (headMaster==null) {
//			throw new HeadMasterNotFoundException("Head master not found with"+" "+id+"!");
//		}
//		return true;
//	}
//	public HeadMaster updateHeadMaster(Long id, HeadMaster headMasterDetails) throws DatabaseException {
//		logger.info("Updating head master details!");
//		Session session=null;
//		HeadMaster headMaster=null;
//		try
//		{
//			boolean checkHeadMaster=checkHeadMaster(id);
//			session=sessionFactory.getCurrentSession();
//			session.find(HeadMaster.class, id);
//			HeadMaster newHeadMasterDetails=session.load(HeadMaster.class, id);
//			newHeadMasterDetails.setFirstName(headMasterDetails.getFirstName());
//			newHeadMasterDetails.setLastName(headMasterDetails.getLastName());
//			newHeadMasterDetails.setDateOfBirth(headMasterDetails.getDateOfBirth());
//			newHeadMasterDetails.setGender(headMasterDetails.getGender());
//			newHeadMasterDetails.setQualification(headMasterDetails.getQualification());
//			newHeadMasterDetails.setMajor(headMasterDetails.getMajor());
//			newHeadMasterDetails.setEmail(headMasterDetails.getEmail());
//			newHeadMasterDetails.setContactNo(headMasterDetails.getContactNo());
//			newHeadMasterDetails.setAddress(headMasterDetails.getAddress());
//			headMaster=(HeadMaster) session.merge(newHeadMasterDetails);
//			logger.info("Head master details updated successfully!");
//		}
//		catch(HibernateException |HeadMasterNotFoundException e)
//		{
//			logger.error("Error while updating the head master details!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return headMaster;
//	}
//	public HeadMaster deleteHeadMaster(Long id) throws DatabaseException {
//		logger.info("Deleting the head master details!");
//		Session session=null;
//		HeadMaster headMaster=null;
//		try
//		{
//			boolean checkHeadMaster=checkHeadMaster(id);
//			session=sessionFactory.getCurrentSession();
//			session.find(HeadMaster.class, id);
//			HeadMaster headMasterDetails=session.load(HeadMaster.class, id);
//			session.delete(headMasterDetails);
//			HeadMaster headMasterEntity=session.get(HeadMaster.class, id);
//			if(headMasterEntity==null)
//			{
//				headMaster=headMasterDetails;
//				logger.info("Head master details deleted successfully!");
//			}
//			else
//			{
//				logger.error("Error while deleting the head master details!");
//			}
//		}
//		catch(HibernateException |HeadMasterNotFoundException e)
//		{
//			logger.error("Error while deleting the head master details!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return headMaster;
//	}
//	public HeadMaster getHeadMaster(Long id) throws DatabaseException {
//		logger.info("Getting head master details!");
//		Session session=null;
//		HeadMaster headMaster=new HeadMaster();
//		try
//		{
//			boolean checkHeadMaster=checkHeadMaster(id);
//			session=sessionFactory.getCurrentSession();
//			Query query=session.createQuery("FROM HeadMaster WHERE id=:headMasterId");
//			query.setParameter("headMasterId", id);
//			headMaster=(HeadMaster) query.getSingleResult();
//			logger.info("Head master details fetched successfully!");
//		}
//		catch(HibernateException | HeadMasterNotFoundException e)
//		{
//			logger.error("Error while fetching head master details!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return headMaster;
//	}
//
//}

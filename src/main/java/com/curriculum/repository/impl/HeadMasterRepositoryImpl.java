package com.curriculum.repository.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.HeadMaster;
import com.curriculum.entity.HeadMasterEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.HeadMasterNotFoundException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.HeadMasterRepository;
import com.curriculum.util.HeadMasterMapper;
import com.curriculum.util.MailSenderUtil;

@Repository
@Transactional
public class HeadMasterRepositoryImpl implements HeadMasterRepository {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private JavaMailSender javaMailSender;
	private Logger logger = Logger.getLogger(HeadMasterRepositoryImpl.class);

	public Long addHeadMaster(HeadMaster headMaster) throws DatabaseException {
		logger.info("Adding the headmaster details...");
		Session session = null;
		Long headMasterId = null;
		try {
			session = sessionFactory.getCurrentSession();
			headMasterId = (Long) session.save(HeadMasterMapper.headMasterMapper(headMaster));
			if (headMasterId > 0) {
				logger.info("Headmaster details added successfully!");
				MailSenderUtil.sendMail(javaMailSender, headMaster.getEmail(), "Regarding Account creation", "Hi"+" "+headMaster.getEmail()+"You have successfully created your account!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding the headmaster!");
			throw new DatabaseException(e.getMessage());
		}
		return headMasterId;
	}

	public void checkHeadMaster(Long id) throws HeadMasterNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<HeadMasterEntity> query = session.createQuery("FROM HeadMasterEntity WHERE id=:headMasterId");
		query.setParameter("headMasterId", id);
		HeadMasterEntity headMaster = query.uniqueResultOptional().orElse(null);
		if (headMaster == null) {
			throw new HeadMasterNotFoundException("Head master not found with" + " " + id + "!");
		}
	}

	public void checkHeadMaster(String email) throws HeadMasterNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<HeadMasterEntity> query = session.createQuery("FROM HeadMasterEntity WHERE email=:email");
		query.setParameter("email", email);
		HeadMasterEntity headMaster = query.uniqueResultOptional().orElse(null);
		if (headMaster == null) {
			throw new HeadMasterNotFoundException("Head master not found with" + " " + email + "!");
		}
	}

	public HeadMasterEntity updateHeadMaster(Long id, HeadMaster headMaster)
			throws DatabaseException, NotFoundException {
		logger.info("Updating head master details...");
		Session session = null;
		HeadMasterEntity updatedHeadMasterEntity = null;
		try {
			checkHeadMaster(id);
			session = sessionFactory.getCurrentSession();
			HeadMasterEntity headMasterDetail = HeadMasterMapper.headMasterMapper(headMaster);
			session.find(HeadMasterEntity.class, id);
			HeadMasterEntity headMasterEntity = session.load(HeadMasterEntity.class, id);
			headMasterEntity.setFirstName(headMasterDetail.getFirstName());
			headMasterEntity.setLastName(headMasterDetail.getLastName());
			headMasterEntity.setDateOfBirth(headMasterDetail.getDateOfBirth());
			headMasterEntity.setGender(headMasterDetail.getGender());
			headMasterEntity.setQualification(headMasterDetail.getQualification());
			headMasterEntity.setMajor(headMasterDetail.getMajor());
			headMasterEntity.setEmail(headMasterDetail.getEmail());
			headMasterEntity.setContactNo(headMasterDetail.getContactNo());
			headMasterEntity.setAddress(headMasterDetail.getAddress());
			headMasterEntity.setPassword(headMasterDetail.getPassword());
			updatedHeadMasterEntity = (HeadMasterEntity) session.merge(headMasterEntity);
			logger.info("Head master details updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the head master details!");
			throw new DatabaseException(e.getMessage());
		}
		return updatedHeadMasterEntity;
	}

	public HeadMasterEntity deleteHeadMaster(Long id) throws DatabaseException, NotFoundException {
		logger.info("Deleting the head master details...");
		Session session = null;
		HeadMasterEntity headMaster = null;
		try {
			checkHeadMaster(id);
			session = sessionFactory.getCurrentSession();
			session.find(HeadMasterEntity.class, id);
			HeadMasterEntity headMasterDetail = session.load(HeadMasterEntity.class, id);
			session.delete(headMasterDetail);
			HeadMasterEntity headMasterEntity = session.get(HeadMasterEntity.class, id);
			if (headMasterEntity == null) {
				headMaster = headMasterDetail;
				logger.info("Head master details deleted successfully!");
			} else {
				logger.error("Error while deleting the head master details!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the head master details!");
			throw new DatabaseException(e.getMessage());
		}
		return headMaster;
	}

	public HeadMasterEntity getHeadMaster(String email) throws DatabaseException, NotFoundException {
		logger.info("Getting head master details by email...");
		Session session = null;
		HeadMasterEntity headMaster = null;
		try {
			checkHeadMaster(email);
			session = sessionFactory.getCurrentSession();
			Query<HeadMasterEntity> query = session.createQuery("FROM HeadMasterEntity WHERE email=:email");
			query.setParameter("email", email);
			headMaster = query.uniqueResultOptional().orElse(null);
			logger.info("Head master details fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching head master details!");
			throw new DatabaseException(e.getMessage());
		}
		return headMaster;
	}

}

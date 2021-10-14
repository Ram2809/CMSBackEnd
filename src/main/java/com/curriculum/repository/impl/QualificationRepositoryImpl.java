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
import org.springframework.stereotype.Repository;

import com.curriculum.dto.Qualification;
import com.curriculum.entity.QualificationEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.repository.QualificationRepository;
import com.curriculum.util.QualificationMapper;


@Repository
@Transactional
public class QualificationRepositoryImpl implements QualificationRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(QualificationRepositoryImpl.class);

	@Override
	public Long addQualification(Qualification qualification) throws DatabaseException {
		logger.info("Adding qualification details...");
		Session session = null;
		Long qualificationId = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			qualificationId = (Long) session.save(QualificationMapper.mapQualification(qualification));
			if (qualificationId > 0) {
				logger.info("Qualification added successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding qualification details!");
			throw new DatabaseException(e.getMessage());
		}
		return qualificationId;
	}

	@Override
	public List<QualificationEntity> getQualifications() throws DatabaseException {
		logger.info("Fetching the qualification details...");
		Session session=null;
		List<QualificationEntity> qualificationList=new ArrayList<>();
		try {
			session=sessionFactory.getCurrentSession();
			Query<QualificationEntity> query=session.createQuery("FROM QualificationEntity");
			qualificationList=query.getResultList();
			logger.info("Qualification details are fetched successfully!");
		}catch (HibernateException e) {
			logger.error("Error while fetching qualification details!");
			throw new DatabaseException(e.getMessage());
		}
		return qualificationList;
	}

}

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
import com.curriculum.entity.MajorEntity;
import com.curriculum.entity.QualificationEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
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
	public void checkQualification(Long id) throws NotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<QualificationEntity> query = session.createQuery("FROM QualificationEntity WHERE id=:id");
		query.setParameter("id", id);
		QualificationEntity qualification = query.uniqueResultOptional().orElse(null);
		if (qualification == null) {
			throw new NotFoundException("Qualification Not Found!");
		}
	}
	@Override
	public QualificationEntity deleteQualification(Long qualificationId) throws DatabaseException, NotFoundException {
		logger.info("Deleting the qualification details...");
		Session session=null;
		QualificationEntity qualificationEntity=null;
		try {
			checkQualification(qualificationId);
			session=sessionFactory.getCurrentSession();
			QualificationEntity qualification=session.load(QualificationEntity.class,qualificationId);
			session.delete(qualification);
			QualificationEntity deletedQualification=session.get(QualificationEntity.class, qualificationId);
			if(deletedQualification==null) {
				qualificationEntity=qualification;
				logger.info("Qualification details are deleted successfully!");
			}
		}
		catch(HibernateException e) {
			logger.error("Error while deleting the qualification details!");
			throw new DatabaseException(e.getMessage());
		}
		return qualificationEntity;
	}

}

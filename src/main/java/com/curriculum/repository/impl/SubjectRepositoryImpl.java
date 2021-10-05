package com.curriculum.repository.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.Subject;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.util.SubjectMapper;

@Repository
@Transactional
public class SubjectRepositoryImpl implements SubjectRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(SubjectRepositoryImpl.class);

	@Override
	public String addSubject(Subject subject) throws DatabaseException {
		logger.info("Adding subject details!");
		Session session = null;
		String subjectCode = null;
		try {
			session = sessionFactory.getCurrentSession();
			subjectCode = (String) session.save(SubjectMapper.subjectMapper(subject));
			if (!subjectCode.isEmpty()) {
				logger.info("Subject details added successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding subject details!");
			throw new DatabaseException(e.getMessage());
		}
		return subjectCode;
	}

	public void checkSubject(String code) throws SubjectNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<SubjectEntity> query = session.createQuery("FROM SubjectEntity WHERE code=:subjectCode");
		query.setParameter("subjectCode", code);
		SubjectEntity subjectEntity = query.uniqueResultOptional().orElse(null);
		if (subjectEntity == null) {
			throw new SubjectNotFoundException("Subject Not Found With" + " " + code + "!");
		}
	}

	@Override
	public SubjectEntity updateSubject(String subjectCode, Subject subject)
			throws DatabaseException, NotFoundException {
		logger.info("Updating the subject details!");
		Session session = null;
		SubjectEntity subjectEntity = null;
		try {
			checkSubject(subjectCode);
			session = sessionFactory.getCurrentSession();
			SubjectEntity subjectDetail = SubjectMapper.subjectMapper(subject);
			session.find(SubjectEntity.class, subjectCode);
			SubjectEntity updatedSubjectEntity = session.load(SubjectEntity.class, subjectCode);
			updatedSubjectEntity.setName(subjectDetail.getName());
			subjectEntity = (SubjectEntity) session.merge(updatedSubjectEntity);
			logger.info("Subject details updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the subject!");
			throw new DatabaseException(e.getMessage());
		}
		return subjectEntity;
	}

	@Override
	public SubjectEntity deleteSubject(String subjectCode) throws DatabaseException, NotFoundException {
		logger.info("Deleting the subject details!");
		Session session = null;
		SubjectEntity subject = null;
		try {
			checkSubject(subjectCode);
			session = sessionFactory.getCurrentSession();
			session.find(SubjectEntity.class, subjectCode);
			SubjectEntity subjectDetail = session.load(SubjectEntity.class, subjectCode);
			session.delete(subjectDetail);
			SubjectEntity subjectEntity = session.get(SubjectEntity.class, subjectCode);
			if (subjectEntity == null) {
				subject = subjectDetail;
				logger.info("Subject is deleted successfully!");
			} else {
				logger.error("Error while deleting the subject!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the subject!");
			throw new DatabaseException(e.getMessage());
		}
		return subject;
	}

	@Override
	public SubjectEntity getParticularSubject(String subjectCode) throws DatabaseException, NotFoundException {
		logger.info("Getting subject details by code!");
		Session session = null;
		SubjectEntity subject = null;
		try {
			checkSubject(subjectCode);
			session = sessionFactory.getCurrentSession();
			Query<SubjectEntity> query = session.createQuery("FROM SubjectEntity WHERE code=:subjectCode");
			query.setParameter("subjectCode", subjectCode);
			subject = query.uniqueResultOptional().orElse(null);
			logger.info("Subject details fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the subject details!");
			throw new DatabaseException(e.getMessage());
		}
		return subject;
	}
}

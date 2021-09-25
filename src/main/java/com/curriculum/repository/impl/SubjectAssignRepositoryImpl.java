package com.curriculum.repository.impl;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.exception.DatabaseException;
import com.curriculum.repository.SubjectAssignRepository;
import com.curriculum.util.SubjectAssignMapper;

@Repository
@Transactional
public class SubjectAssignRepositoryImpl implements SubjectAssignRepository {
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Long addSubjectAssign(SubjectAssign subjectAssign) throws DatabaseException {
		Session session = null;
		Long id = null;
		try {
			session = sessionFactory.getCurrentSession();
			id = (Long) session.save(SubjectAssignMapper.mapSubjectAssign(subjectAssign));
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
		return id;
	}

}

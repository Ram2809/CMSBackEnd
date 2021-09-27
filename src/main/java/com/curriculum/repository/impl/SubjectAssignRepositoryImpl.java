package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Query;
import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.entity.SubjectEntity;
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

	@Override
	public List<SubjectAssignEntity> getSubjects(Long roomNo) throws DatabaseException {
		Session session=null;
		List<SubjectAssignEntity> subjectList=new ArrayList<>();
		try
		{
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("FROM SubjectAssignEntity s WHERE s.classEntity.roomNo=:roomNo");
			query.setParameter("roomNo", roomNo);
			subjectList=query.getResultList();
		}
		catch(HibernateException e)
		{
			throw new DatabaseException(e.getMessage());
		}
		return subjectList;
	}

}

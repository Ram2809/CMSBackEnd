package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.entity.TeacherAssignEntity;
import com.curriculum.exception.AssignIdNotFoundException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
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
		Session session = null;
		List<SubjectAssignEntity> subjectList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query<SubjectAssignEntity> query = session
					.createQuery("FROM SubjectAssignEntity s WHERE s.classDetail.roomNo=:roomNo");
			query.setParameter("roomNo", roomNo);
			subjectList = query.getResultList();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
		return subjectList;
	}

	@Override
	public Long getAssignId(Long roomNo, String subjectCode) throws DatabaseException {
		Session session = null;
		Long assignId = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query<Long> query = session.createQuery(
					"SELECT s.id FROM SubjectAssignEntity s WHERE s.classDetail.roomNo=:roomNo AND s.subject.code=:code");
			query.setParameter("roomNo", roomNo);
			query.setParameter("code", subjectCode);
			assignId = (Long) query.getSingleResult();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
		return assignId;
	}

	@Override
	public String getSubjectCode(Long id) throws DatabaseException, NotFoundException {
		Session session = null;
		String subjectCode = "";
		try {
			checkAssignId(id);
			session = sessionFactory.getCurrentSession();
			Query<String> query = session
					.createQuery("SELECT s.subject.code FROM SubjectAssignEntity s WHERE s.id=:id");
			query.setParameter("id", id);
			subjectCode = query.uniqueResult();
		} catch (HibernateException e) {
			throw new DatabaseException(e.getMessage());
		}
		return subjectCode;
	}

	public void checkAssignId(Long id) throws AssignIdNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<SubjectAssignEntity> query = session.createQuery("FROM SubjectAssignEntity WHERE id=:id");
		query.setParameter("id", id);
		SubjectAssignEntity subjectAssignEntity = query.uniqueResultOptional().orElse(null);
		if (subjectAssignEntity == null) {
			throw new AssignIdNotFoundException("Assign id is not found");
		}
	}

}

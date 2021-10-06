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

import com.curriculum.dto.TeacherAssign;
import com.curriculum.entity.TeacherAssignEntity;
import com.curriculum.exception.AssignIdNotFoundException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.TeacherAssignRepository;
import com.curriculum.util.TeacherAssignMapper;

@Repository
@Transactional
public class TeacherAssignRepositoryImpl implements TeacherAssignRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(TeacherAssignRepositoryImpl.class);

	@Override
	public Long assignTeacherSubject(TeacherAssign teacherAssign) throws DatabaseException {
		logger.info("Assigning teacher for particular course!");
		Session session = null;
		Long assignId = null;
		try {
			session = sessionFactory.getCurrentSession();
			assignId = (Long) session.save(TeacherAssignMapper.teacherAssignMapper(teacherAssign));
			if (assignId > 0) {
				logger.info("Teacher assigned for course successfully!");
			}
			logger.info("Teacher assigned for course successfully!");
		} catch (HibernateException e) {
			logger.error("Error while assigning staff for course!");
			throw new DatabaseException(e.getMessage());
		}
		return assignId;
	}

	public void checkAssignId(Long id) throws AssignIdNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<TeacherAssignEntity> query = session.createQuery("FROM TeacherAssignEntity WHERE id=:id");
		query.setParameter("id", id);
		TeacherAssignEntity teacherSubject = query.uniqueResultOptional().orElse(null);
		if (teacherSubject == null) {
			throw new AssignIdNotFoundException("Assign id is not found");
		}
	}

	@Override
	public TeacherAssignEntity deleteTeacherSubjectAssign(Long id) throws DatabaseException, NotFoundException {
		logger.info("Deleting the teacher subject assign!");
		TeacherAssignEntity deletedTeacherSubjectAssign = null;
		Session session = null;
		try {
			checkAssignId(id);
			session = sessionFactory.getCurrentSession();
			session.find(TeacherAssignEntity.class, id);
			TeacherAssignEntity teacherAssignEntity = session.load(TeacherAssignEntity.class, id);
			session.delete(teacherAssignEntity);
			TeacherAssignEntity teacherSubjectAssign = session.get(TeacherAssignEntity.class, id);
			if (teacherSubjectAssign == null) {
				deletedTeacherSubjectAssign = teacherAssignEntity;
				logger.info("Teacher subject assign is deleted successfully!");
			} else {
				logger.error("Error while deleting the teacher subject assign!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the teacher subject assign!");
			throw new DatabaseException(e.getMessage());
		}
		return deletedTeacherSubjectAssign;
	}

	@Override
	public List<Long> getSubjectAssignIds(Long staffId) throws DatabaseException {
		logger.info("Getting teacher assign details!");
		Session session = null;
		List<Long> subjectAssignIdList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query<Long> query = session
					.createQuery("SELECT t.subjectAssign.id FROM TeacherAssignEntity t WHERE t.teacher.id=:id");
			query.setParameter("id", staffId);
			subjectAssignIdList = query.list();
			logger.info("Teacher assign details fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching teacher assign details!");
			throw new DatabaseException(e.getMessage());
		}
		return subjectAssignIdList;
	}

	@Override
	public Long getTeacherId(Long id) throws DatabaseException {
		logger.info("Getting teacher assign details!");
		Session session = null;
		Long teacherId = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query<Long> query = session
					.createQuery("SELECT t.teacher.id FROM TeacherAssignEntity t WHERE t.subjectAssign.id=:id");
			query.setParameter("id", id);
			teacherId = query.getSingleResult();
			logger.info("Teacher assign details fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching teacher assign details!");
			throw new DatabaseException(e.getMessage());
		}
		return teacherId;
	}

	@Override
	public Long updateTeacherAssign(Long assignId, Long staffId) throws DatabaseException {
		logger.info("Updating teacher assign details...");
		Session session = null;
		Long count = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			Query<Long> query = session.createQuery(
					"UPDATE TeacherAssignEntity t SET t.teacher.id=:teacherId WHERE t.subjectAssign.id=:assignId");
			query.setParameter("teacherId", staffId);
			query.setParameter("assignId", assignId);
			count = (long) query.executeUpdate();
			logger.info("Teacher assign details updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching teacher assign details!");
			throw new DatabaseException(e.getMessage());
		}
		return count;
	}

	@Override
	public List<Long> getTeacherIdList(List<Long> assignIdList) throws DatabaseException {
		// TODO Auto-generated method stub
		return null;
	}

//	@Override
//	public List<Long> getTeacherIdList(List<Long> assignIdList) throws DatabaseException {
//		logger.info("Getting teacher assign details!");
//		Session session = null;
//		List<Long> teacherIdList = null;
//		try {
//			session = sessionFactory.getCurrentSession();
//			for (Long assignId : assignIdList) {
//				Query<Long> query = session
//						.createQuery("SELECT t.teacher.id FROM TeacherAssignEntity t WHERE t.subjectAssign.id=:id");
//				query.setParameter("id", assignId);
//				Long teacherId = query.uniqueResult();
//				if(teacherId>0)
//				{
//					teacherIdList.add(teacherId);
//				}
//			}
//			logger.info("Teacher assign details fetched successfully!");
//		} catch (HibernateException e) {
//			logger.error("Error while fetching teacher assign details!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return teacherIdList;
//	}
}

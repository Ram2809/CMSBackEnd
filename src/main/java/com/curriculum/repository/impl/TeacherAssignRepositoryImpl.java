package com.curriculum.repository.impl;

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
		} catch (HibernateException e) {
			logger.error("Error while assigning staff for course!");
			throw new DatabaseException(e.getMessage());
		}
		return assignId;
	}

//	@Override
//	public TeacherAssignEntity updateTeacherSubjectAssign(Long id, TeacherAssign teacherAssign)
//			throws DatabaseException, NotFoundException {
//		logger.info("Updating the teacher for course!");
//		TeacherAssignEntity updatedTeacherSubject = null;
//		Session session = null;
//		try {
//			checkAssignId(id);
//			session = sessionFactory.getCurrentSession();
//			TeacherAssignEntity teacherSubjectDetail = TeacherSubjectMapper.teacherSubjectMapper(teacherAssign);
//			session.find(TeacherAssignEntity.class, id);
//			TeacherAssignEntity teacherAssignEntity = session.load(TeacherAssignEntity.class, id);
//			TeacherEntity teacherEntity = new TeacherEntity();
//			teacherEntity.setId(teacherSubjectDetail.getTeacher().getId());
//			teacherAssignEntity.setTeacher(teacherEntity);
//			SubjectEntity subjectEntity = new SubjectEntity();
//			subjectEntity.setCode(teacherSubjectDetail.getSubject().getCode());
//			teacherAssignEntity.setSubject(subjectEntity);
//			updatedTeacherSubject = (TeacherAssignEntity) session.merge(teacherAssignEntity);
//			logger.info("Staff assigned for new subject successfully!");
//		} catch (HibernateException e) {
//			logger.error("Error while updating the staff  for new subject!");
//			throw new DatabaseException(e.getMessage());
//		}
//		return updatedTeacherSubject;
//	}

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
	public TeacherAssignEntity updateTeacherSubjectAssign(Long id, TeacherAssign teacherAssign)
			throws DatabaseException, NotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}

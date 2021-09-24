package com.curriculum.repository.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.TeacherSubject;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.entity.TeacherSubjectEntity;
import com.curriculum.exception.AssignIdNotFoundException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.StudentNotFoundException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.repository.TeacherSubjectRepository;
import com.curriculum.util.TeacherSubjectMapper;
@Repository
@Transactional
public class TeacherSubjectRepositoryImpl implements TeacherSubjectRepository{
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger=Logger.getLogger(TeacherSubjectRepositoryImpl.class);
	@Override
	public Long assignTeacherSubject(TeacherSubject teacherSubject) throws DatabaseException{
		logger.info("Assigning teacher for particular course!");
		Session session=null;
		Long assignId=null;
		try
		{
			session=sessionFactory.getCurrentSession();
			assignId=(Long) session.save(TeacherSubjectMapper.teacherSubjectMapper(teacherSubject));
			if(assignId>0)
			{
				logger.info("Teacher assigned for course successfully!");
			}
		}
		catch(HibernateException e)
		{
			logger.error("Error while assigning staff for course!");
			throw new DatabaseException(e.getMessage());
		}
		return assignId;
	}
	@Override
	public TeacherSubjectEntity updateTeacherSubjectAssign(Long id,TeacherSubject teacherSubject) throws DatabaseException, NotFoundException{
		logger.info("Updating the teacher for course!");
		TeacherSubjectEntity updatedTeacherSubject=null;
		Session session=null;
		try
		{
			checkAssignId(id);
			session=sessionFactory.getCurrentSession();
			TeacherSubjectEntity teacherSubjectDetail=TeacherSubjectMapper.teacherSubjectMapper(teacherSubject);
			session.find(TeacherSubjectEntity.class, id);
			TeacherSubjectEntity teacherSubjectEntity=session.load(TeacherSubjectEntity.class, id);
			TeacherEntity teacherEntity=new TeacherEntity();
			teacherEntity.setId(teacherSubjectDetail.getTeacher().getId());
			teacherSubjectEntity.setTeacher(teacherEntity);
			SubjectEntity subjectEntity=new SubjectEntity();
			subjectEntity.setCode(teacherSubjectDetail.getSubject().getCode());
			teacherSubjectEntity.setSubject(subjectEntity);
			updatedTeacherSubject=(TeacherSubjectEntity) session.merge(teacherSubjectEntity);
			logger.info("Staff assigned for new subject successfully!");
		}
		catch(HibernateException e)
		{
			logger.error("Error while updating the staff  for new subject!");
			throw new DatabaseException(e.getMessage());
		}
		return updatedTeacherSubject;
	}
	public void checkAssignId(Long id) throws AssignIdNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<TeacherSubjectEntity> query = session.createQuery("FROM TeacherSubjectEntity WHERE id=:id");
		query.setParameter("id", id);
		TeacherSubjectEntity teacherSubject =  query.uniqueResultOptional().orElse(null);
		if (teacherSubject==null) {
			throw new AssignIdNotFoundException("Assign id is not found");
		}
	}
	@Override
	public TeacherSubjectEntity deleteTeacherSubjectAssign(Long id) throws DatabaseException, NotFoundException {
		logger.info("Deleting the teacher subject assign!");
		TeacherSubjectEntity deletedTeacherSubjectAssign=null;
		Session session=null;
		try
		{
			checkAssignId(id);
			session=sessionFactory.getCurrentSession();
			session.find(TeacherSubjectEntity.class, id);
			TeacherSubjectEntity teacherSubjectEntity=session.load(TeacherSubjectEntity.class, id);
			session.delete(teacherSubjectEntity);
			TeacherSubjectEntity teacherSubjectAssign=session.get(TeacherSubjectEntity.class, id);
			if(teacherSubjectAssign==null)
			{
				deletedTeacherSubjectAssign=teacherSubjectEntity;
				logger.info("Teacher subject assign is deleted successfully!");
			}
			else
			{
				logger.error("Error while deleting the teacher subject assign!");
			}
		}
		catch(HibernateException  e)
		{
			logger.error("Error while deleting the teacher subject assign!");
			throw new DatabaseException(e.getMessage());
		}
		return deletedTeacherSubjectAssign;
	}
}

package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.curriculum.dto.Teacher;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.repository.TeacherRepository;
import com.curriculum.util.TeacherMapper;

@Repository
@Transactional
public class TeacherRepositoryImpl implements TeacherRepository {
	static Logger logger = Logger.getLogger(TeacherRepositoryImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Long addTeacher(Teacher teacher) throws DatabaseException {
		logger.info("Adding the teacher details...S");
		Session session = null;
		Long staffId = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			staffId = (Long) session.save(TeacherMapper.teacherMapper(teacher));
			if (staffId > 0) {
				logger.info("Teacher details are added successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding the teacher!");
			throw new DatabaseException(e.getMessage());
		}
		return staffId;
	}

	@Override
	public List<TeacherEntity> getAllTeacher() throws DatabaseException {
		logger.info("Getting all teacher details...");
		Session session = null;
		List<TeacherEntity> teacherList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query<TeacherEntity> query = session.createQuery("FROM TeacherEntity t");
			teacherList = query.getResultList();
			logger.info("Teacher list is fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the teacher list!");
			throw new DatabaseException(e.getMessage());
		}
		return teacherList;
	}

	public void checkTeacher(Long id) throws TeacherNotFoundException {
		TeacherEntity teacherEntity = null;
		Session session = sessionFactory.getCurrentSession();
		Query<TeacherEntity> query = session.createQuery("FROM TeacherEntity WHERE id=:teacherId");
		query.setParameter("teacherId", id);
		teacherEntity = query.uniqueResultOptional().orElse(null);
		if (teacherEntity == null) {
			throw new TeacherNotFoundException("Teacher Not Found With" + " " + id + "!");
		}
	}

	public void checkTeacher(String email) throws TeacherNotFoundException {
		TeacherEntity teacherEntity = null;
		Session session = sessionFactory.getCurrentSession();
		Query<TeacherEntity> query = session.createQuery("FROM TeacherEntity WHERE email=:email");
		query.setParameter("email", email);
		teacherEntity = query.uniqueResultOptional().orElse(null);
		if (teacherEntity == null) {
			throw new TeacherNotFoundException("Invalid email!");
		}
	}

	@Override
	public TeacherEntity updateTeacher(Long id, Teacher teacher) throws DatabaseException, NotFoundException {
		logger.info("Updating the teacher details...");
		TeacherEntity response = null;
		Session session = null;
		try {
			checkTeacher(id);
			session = sessionFactory.getCurrentSession();
			TeacherEntity teacherDetail = TeacherMapper.teacherMapper(teacher);
			session.find(TeacherEntity.class, id);
			TeacherEntity updatedTeacherEntity = session.load(TeacherEntity.class, id);
			updatedTeacherEntity.setFirstName(teacherDetail.getFirstName());
			updatedTeacherEntity.setLastName(teacherDetail.getLastName());
			updatedTeacherEntity.setDateOfBirth(teacherDetail.getDateOfBirth());
			updatedTeacherEntity.setGender(teacherDetail.getGender());
			updatedTeacherEntity.setQualification(teacherDetail.getQualification());
			updatedTeacherEntity.setMajor(teacherDetail.getMajor());
			updatedTeacherEntity.setEmail(teacherDetail.getEmail());
			updatedTeacherEntity.setContactNo(teacherDetail.getContactNo());
			updatedTeacherEntity.setAddress(teacherDetail.getAddress());
			response = (TeacherEntity) session.merge(updatedTeacherEntity);
			logger.info("Teacher Details are updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the teacher!");
			throw new DatabaseException(e.getMessage());
		}
		return response;
	}

	@Override
	public TeacherEntity deleteTeacher(Long id) throws DatabaseException, NotFoundException {
		logger.info("Deleting the teacher details...");
		Session session = null;
		TeacherEntity deletedTeacherEntity = null;
		try {
			checkTeacher(id);
			session = sessionFactory.getCurrentSession();
			session.find(TeacherEntity.class, id);
			TeacherEntity teacherEntity = session.load(TeacherEntity.class, id);
			session.delete(teacherEntity);
			TeacherEntity teacherDetail = session.get(TeacherEntity.class, id);
			if (teacherDetail == null) {
				deletedTeacherEntity = teacherEntity;
				logger.info("Teacher details are deleted successfully!");
			} else {
				logger.error("Error while deleting the teacher details!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the teacher details!");
			throw new DatabaseException(e.getMessage());
		}

		return deletedTeacherEntity;
	}

	@Override
	public TeacherEntity getParticularTeacher(Long id) throws DatabaseException, NotFoundException {
		logger.info("Getting the teacher detail...");
		Session session = null;
		TeacherEntity teacherEntity = null;
		try {
			checkTeacher(id);
			session = sessionFactory.getCurrentSession();
			Query<TeacherEntity> query = session.createQuery("FROM TeacherEntity WHERE id=:teacherId");
			query.setParameter("teacherId", id);
			teacherEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Teacher details are fetched succesfully!");
		} catch (HibernateException e) {
			logger.error("Error while fecthing the teacher detail!");
			throw new DatabaseException(e.getMessage());
		}
		return teacherEntity;
	}

	@Override
	public TeacherEntity getTeacherByEmail(String email) throws DatabaseException, NotFoundException {
		logger.info("Getting the teacher detail...");
		Session session = null;
		TeacherEntity teacherEntity = null;
		try {
			checkTeacher(email);
			session = sessionFactory.getCurrentSession();
			Query<TeacherEntity> query = session.createQuery("FROM TeacherEntity WHERE email=:email");
			query.setParameter("email", email);
			teacherEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Teacher details are fetched succesfully!");
		} catch (HibernateException e) {
			logger.error("Error while fecthing the teacher detail!");
			throw new DatabaseException(e.getMessage());
		}
		return teacherEntity;
	}

	@Override
	public List<TeacherEntity> getTeacherList(List<Long> teacherIdList) throws DatabaseException, NotFoundException {
		logger.info("Getting teacher details list...");
		Session session=null;
		List<TeacherEntity> teachersList=new ArrayList<>();
		try {
			session=sessionFactory.getCurrentSession();
			for(Long teacherId:teacherIdList) {
				checkTeacher(teacherId);
				Query<TeacherEntity> query=session.createQuery("FROM TeacherEntity WHERE id=:staffId");
				query.setParameter("staffId", teacherId);
				TeacherEntity teacherEntity=query.uniqueResultOptional().orElse(null);
				if(teacherEntity!=null) {
					teachersList.add(teacherEntity);
				}
			}
			logger.info("Teacher details are fetched successfully!");
		}
		catch(HibernateException e)
		{
			logger.error("Error while fecthing teacher detail!");
			throw new DatabaseException(e.getMessage());
		}
		return teachersList;
	}

}

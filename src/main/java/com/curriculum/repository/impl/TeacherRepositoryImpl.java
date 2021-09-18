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

import com.curriculum.entity.Teacher;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.repository.TeacherRepository;

@Repository
@Transactional
public class TeacherRepositoryImpl implements TeacherRepository {
	static Logger logger = Logger.getLogger(TeacherRepositoryImpl.class);
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public Teacher addTeacher(Teacher teacher) throws DatabaseException {
		logger.info("Adding teacher");
		Session session = null;
		Long count = 0l;
		Teacher response = null;
		try {
			session = sessionFactory.getCurrentSession();
			if (teacher.getMajor() == null) {
				throw new ConstraintValidationException("Major must be entered!");
			}
			count = (Long) session.save(teacher);
			if (count > 0) {
				response = teacher;
				logger.info("Teacher details added successfully!");
			}
		} catch (HibernateException | ConstraintValidationException e) {
			logger.error("Error while adding the teacher!");
			throw new DatabaseException(e.getMessage());
		}
		return response;
	}

	@Override
	public List<Teacher> getAllTeacher() throws DatabaseException {
		logger.info("Getting all teacher details");
		Session session = null;
		List<Teacher> teacherList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query<Teacher> query = session.createQuery("FROM Login t");
			teacherList = query.list();
			logger.info("Teacher list is fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the teacher list");
			throw new DatabaseException(e.getMessage());
		}
		return teacherList;
	}

	public boolean checkTeacher(Long id) throws TeacherNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<Teacher> query = session.createQuery("FROM Teacher WHERE id=:teacherId");
		query.setParameter("teacherId", id);
		List<Teacher> teacherList = query.list();
		if (teacherList.isEmpty()) {
			throw new TeacherNotFoundException("Teacher Not Found With" + " " + id + "!");
		}
		return true;
	}

	@Override
	public Teacher updateTeacher(Long id, Teacher teacher) throws DatabaseException {
		logger.info("Updating the teacher details");
		Teacher response = null;
		Session session = null;
		try {
			boolean checkTeacher = checkTeacher(id);
			session = sessionFactory.getCurrentSession();
			session.find(Teacher.class, id);
			Teacher newTeacher = session.load(Teacher.class, id);
			newTeacher.setFirstName(teacher.getFirstName());
			newTeacher.setLastName(teacher.getLastName());
			newTeacher.setDateOfBirth(teacher.getDateOfBirth());
			newTeacher.setGender(teacher.getGender());
			newTeacher.setQualification(teacher.getQualification());
			System.out.println(teacher.getMajor());
			if (teacher.getMajor() == null) {
				throw new ConstraintValidationException("Major must be entered!");
			}
			newTeacher.setMajor(teacher.getMajor());
			newTeacher.setEmail(teacher.getEmail());
			newTeacher.setContactNo(teacher.getContactNo());
			newTeacher.setAddress(teacher.getAddress());
			response = (Teacher) session.merge(newTeacher);
			logger.info("Teacher Details updated successfully!");
		} catch (HibernateException | TeacherNotFoundException | ConstraintValidationException e) {
			logger.error("Error while updating the teacher!");
			throw new DatabaseException(e.getMessage());
		}
		return response;
	}

	@Override
	public Teacher deleteTeacher(Long id) throws DatabaseException {
		logger.info("Deleting the teacher");
		Session session = null;
		Teacher response = null;
		try {
			boolean checkTeacher = checkTeacher(id);
			session = sessionFactory.getCurrentSession();
			session.find(Teacher.class, id);
			Teacher teacher = session.load(Teacher.class, id);
			session.delete(teacher);
			Teacher teacherDetail = session.get(Teacher.class, id);
			if (teacherDetail == null) {
				response = teacher;
				logger.info("Teacher Deleted Successfully!");
			} else {
				logger.error("Error while deleting the teacher!");
			}
		} catch (HibernateException | TeacherNotFoundException e) {
			logger.error("Error while deleting the teacher!");
			throw new DatabaseException(e.getMessage());
		}

		return response;
	}

	@Override
	public Teacher getParticularTeacher(Long id) throws DatabaseException {
		logger.info("Getting teacher detail");
		Session session = null;
		Teacher teacher = new Teacher();
		try {
			boolean checkTeacher = checkTeacher(id);
			session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM Teacher WHERE id=:teacherId");
			query.setParameter("teacherId", id);
			teacher = (Teacher) query.getSingleResult();
			logger.info("Teacher details fetched succesfully!");
		} catch (HibernateException | TeacherNotFoundException e) {
			logger.error("Error while fecthing teacher detail!");
			;
			throw new DatabaseException(e.getMessage());
		}
		return teacher;
	}

}

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

import com.curriculum.entity.Student;
import com.curriculum.entity.Subject;
import com.curriculum.entity.Teacher;
import com.curriculum.entity.TeacherSubject;
import com.curriculum.exception.AssignIdNotFoundException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.StudentNotFoundException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.repository.TeacherSubjectRepository;
@Repository
@Transactional
public class TeacherSubjectRepositoryImpl implements TeacherSubjectRepository{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TeacherRepositoryImpl teacherRepositoryImpl;
	@Autowired
	private SubjectRepositoryImpl subjectRepositoryImpl;
	private Logger logger=Logger.getLogger(TeacherSubjectRepositoryImpl.class);
	@Override
	public TeacherSubject assignTeacherSubject(TeacherSubject teacherSubjectDetails) throws DatabaseException{
		logger.info("Assigning teacher for particular course!");
		Session session=null;
		TeacherSubject teacherSubjectAssign=null;
		try
		{
			boolean checkTeacher=teacherRepositoryImpl.checkTeacher(teacherSubjectDetails.getTeacher().getId());
			boolean checkSubject=subjectRepositoryImpl.checkSubject(teacherSubjectDetails.getSubject().getCode());
			System.out.println(teacherSubjectDetails.getTeacher().getId());
			System.out.println(teacherSubjectDetails.getSubject().getCode());
			session=sessionFactory.getCurrentSession();
			Teacher teacherDetails=new Teacher();
			teacherDetails.setId(teacherSubjectDetails.getTeacher().getId());
			Subject subjectDetails=new Subject();
			subjectDetails.setCode(teacherSubjectDetails.getSubject().getCode());
			TeacherSubject teacherSubjectAssignDetails=new TeacherSubject();
			teacherSubjectAssignDetails.setTeacher(teacherDetails);
			teacherSubjectAssignDetails.setSubject(subjectDetails);
			Long count=(Long) session.save(teacherSubjectAssignDetails);
			if(count>0)
			{
				teacherSubjectAssign=teacherSubjectAssignDetails;
				logger.info("Teacher assigned for course successfully!");
			}
		}
		catch(HibernateException |TeacherNotFoundException| SubjectNotFoundException e)
		{
			logger.error("Error while assigning staff for course!");
			throw new DatabaseException(e.getMessage());
		}
		return teacherSubjectAssign;
	}
	@Override
	public TeacherSubject updateTeacherSubjectAssign(Long id,TeacherSubject teacherSubjectDetails) throws DatabaseException{
		logger.info("Updating the teacher for course!");
		TeacherSubject teacherSubject=null;
		Session session=null;
		try
		{
			boolean checkId=checkAssignId(id);
			boolean checkTeacher=teacherRepositoryImpl.checkTeacher(teacherSubjectDetails.getTeacher().getId());
			boolean checkSubject=subjectRepositoryImpl.checkSubject(teacherSubjectDetails.getSubject().getCode());
			session=sessionFactory.getCurrentSession();
			session.find(TeacherSubject.class, id);
			TeacherSubject teacherSubjectEntity=session.load(TeacherSubject.class, id);
			Teacher teacher=new Teacher();
			teacher.setId(teacherSubjectDetails.getTeacher().getId());
			Subject subject=new Subject();
			subject.setCode(teacherSubjectDetails.getSubject().getCode());
			teacherSubjectEntity.setTeacher(teacher);
			teacherSubjectEntity.setSubject(subject);
			teacherSubject=(TeacherSubject) session.merge(teacherSubjectEntity);
			logger.info("Staff assigned for new subject successfully!");
		}
		catch(HibernateException |AssignIdNotFoundException|SubjectNotFoundException |TeacherNotFoundException e)
		{
			logger.error("Error while updating the staff  for new subject!");
			throw new DatabaseException(e.getMessage());
		}
		return teacherSubject;
	}
	public boolean checkAssignId(Long id) throws AssignIdNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM TeacherSubject WHERE id=:id");
		query.setParameter("id", id);
		TeacherSubject teacherSubject = (TeacherSubject) query.uniqueResultOptional().orElse(null);
		if (teacherSubject==null) {
			throw new AssignIdNotFoundException("Assign id is not found");
		}
		return true;
	}
	@Override
	public TeacherSubject deleteTeacherSubjectAssign(Long id) throws DatabaseException {
		logger.info("Deleting the teacher subject assign!");
		TeacherSubject teacherSubject=null;
		Session session=null;
		try
		{
			boolean checkId=checkAssignId(id);
			session=sessionFactory.getCurrentSession();
			session.find(TeacherSubject.class, id);
			TeacherSubject teacherSubjectEntity=session.load(TeacherSubject.class, id);
			session.delete(teacherSubjectEntity);
			TeacherSubject teacherSubjectDetails=session.get(TeacherSubject.class, id);
			if(teacherSubjectDetails==null)
			{
				teacherSubject=teacherSubjectEntity;
				logger.info("Teacher subject assign is deleted successfully!");
			}
			else
			{
				logger.error("Error while deleting the teacher subject assign!");
			}
		}
		catch(HibernateException | AssignIdNotFoundException e)
		{
			logger.error("Error while deleting the teacher subject assign!");
			throw new DatabaseException(e.getMessage());
		}
		return teacherSubject;
	}
}

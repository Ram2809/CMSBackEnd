package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.curriculum.entity.Teacher;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.repository.TeacherRepository;
@Repository
@Transactional
public class TeacherRepositoryImpl implements TeacherRepository{
	@Autowired
	private SessionFactory sessionFactory;
	@Override
	public ResponseEntity<String> addTeacherDetails(Teacher teacherDeteails) {
		// TODO Auto-generated method stub
		Session session=null;
		ResponseEntity<String> response=null;
		try
		{
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.save(teacherDeteails);
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Teacher Details Added Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return response;
	}
	@Override
	public ResponseEntity<List<Teacher>> getAllTeacherDetails() {
		// TODO Auto-generated method stub
		ResponseEntity<List<Teacher>> response=null;
		Session session=null;
		List<Teacher> teacherDetailsList=new ArrayList<>();
		try
		{
			session=sessionFactory.openSession();
			Query query=session.createQuery("FROM Teacher t");
			teacherDetailsList=query.list();
			response=new ResponseEntity<List<Teacher>>(teacherDetailsList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return response;
	}
	public boolean checkTeacher(Long id) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Teacher WHERE id=:teacherId");
		query.setParameter("teacherId", id);
		List<Teacher> teacherList = query.list();
		if (teacherList.isEmpty()) {
			return false;
		}
		return true;
	}
	@Override
	public ResponseEntity<String> updateTeacherDetails(Long id, Teacher teacherDetails) throws  DatabaseException {
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean checkTeacher=checkTeacher(id);
			if(!checkTeacher)
			{
				throw new TeacherNotFoundException("Teacher Not Found with"+" "+id+"!");
			}
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.find(Teacher.class, id);
			Teacher newTeacherDetails=session.load(Teacher.class, id);
			newTeacherDetails.setFirstName(teacherDetails.getFirstName());
			newTeacherDetails.setLastName(teacherDetails.getLastName());
			newTeacherDetails.setDateOfBirth(teacherDetails.getDateOfBirth());
			newTeacherDetails.setGender(teacherDetails.getGender());
			newTeacherDetails.setQualification(teacherDetails.getQualification());
			newTeacherDetails.setMajor(teacherDetails.getMajor());
			newTeacherDetails.setEmail(teacherDetails.getEmail());
			newTeacherDetails.setContactNo(teacherDetails.getContactNo());
			newTeacherDetails.setAddress(teacherDetails.getAddress());
			session.merge(newTeacherDetails);
			session.flush();
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Teacher Details Updated Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException |TeacherNotFoundException e)
		{
			throw new DatabaseException(e.getMessage());
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return response;
	}
	@Override
	public ResponseEntity<String> deleteTeacherDetails(Long id) {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean checkTeacher=checkTeacher(id);
			if(!checkTeacher)
			{
				throw new TeacherNotFoundException("Teacher Not Found with"+" "+id+"!");
			}
			session=sessionFactory.openSession();
			session.beginTransaction();
			session.find(Teacher.class, id);
			Teacher teacherDetails=session.load(Teacher.class, id);
			session.delete(teacherDetails);
			session.flush();
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Teacher Details Deleted Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException |TeacherNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return response;
	}
	@Override
	public ResponseEntity<Teacher> getParticularTeacherDetails(Long id) {
		// TODO Auto-generated method stub
		ResponseEntity<Teacher> response=null;
		Session session=null;
		Teacher teacherDetails=new Teacher();
		try
		{
			boolean checkTeacher=checkTeacher(id);
			if(!checkTeacher)
			{
				throw new TeacherNotFoundException("Teacher Not Found with"+" "+id+"!");
			}
			session=sessionFactory.openSession();
			Query query=session.createQuery("FROM Teacher WHERE id=:teacherId");
			query.setParameter("teacherId", id);
			teacherDetails=(Teacher) query.getSingleResult();
			response=new ResponseEntity<Teacher>(teacherDetails,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | TeacherNotFoundException e)
		{
			e.printStackTrace();
		}
		finally
		{
			if(session!=null)
			{
				session.close();
			}
		}
		return response;
	}

}

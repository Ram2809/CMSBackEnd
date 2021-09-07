package com.curriculum.repository.impl;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Repository;

import com.curriculum.entity.Login;
import com.curriculum.entity.Teacher;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.repository.LoginRepository;
@Repository
public class LoginRepositoryImpl implements LoginRepository{
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TeacherRepositoryImpl teacherRepositoryImpl;
	@Override
	public ResponseEntity<String> addLogin(Long teacherId, Login loginDetails) {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean teacherStatus=teacherRepositoryImpl.checkTeacher(teacherId);
			if(!teacherStatus)
			{
				throw new TeacherNotFoundException("Teacher Not Found with"+" "+teacherId+"!");
			}
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			Teacher teacher=new Teacher();
			teacher.setId(teacherId);
			Login login=new Login();
			login.setLoginId(loginDetails.getLoginId());
			login.setTeacher(teacher);
			login.setPassword(loginDetails.getPassword());
			session.save(login);
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Login Details Created Successfully!",new HttpHeaders(),HttpStatus.OK);
		}
		catch( HibernateException | TeacherNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
	@Override
	public ResponseEntity<Login> getLoginDetails(Long teacherId) {
		// TODO Auto-generated method stub
		ResponseEntity<Login> response=null;
		Session session=null;
		Login loginDetails=new Login();
		try
		{
			boolean teacherStatus=teacherRepositoryImpl.checkTeacher(teacherId);
			if(!teacherStatus)
			{
				throw new TeacherNotFoundException("Teacher Not Found with"+" "+teacherId+"!");
			}
			session=sessionFactory.getCurrentSession();
			Query query=session.createQuery("FROM Login WHERE teacher_id=:teacherId");
			query.setParameter("teacherId", teacherId);
			loginDetails=(Login) query.getSingleResult();
			response=new ResponseEntity<Login>(loginDetails,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | TeacherNotFoundException e)
		{
			e.printStackTrace();
		}
		return response;
	}
	@Override
	public ResponseEntity<String> updateLoginDetails(Long teacherId, Login loginDetails) {
		// TODO Auto-generated method stub
		ResponseEntity<String> response=null;
		Session session=null;
		try
		{
			boolean teacherStatus=teacherRepositoryImpl.checkTeacher(teacherId);
			if(!teacherStatus)
			{
				throw new TeacherNotFoundException("Teacher Not Found with"+" "+teacherId+"!");
			}
			session=sessionFactory.getCurrentSession();
			session.beginTransaction();
			String password=loginDetails.getPassword();
			if(password.length()<8)
			{
				response=new ResponseEntity<String>("Password must contain 8 digits!",new HttpHeaders(),HttpStatus.OK);
			}
			else {
			Query query=session.createQuery("UPDATE Login SET password=:password WHERE teacher_id=:teacherId");
			query.setParameter("password", password);
			query.setParameter("teacherId", teacherId);
			query.executeUpdate();
			session.getTransaction().commit();
			response=new ResponseEntity<String>("Password Changed Successfully!",new HttpHeaders(),HttpStatus.OK);
			}
		}
		catch( HibernateException | TeacherNotFoundException e)
		{
			response=new ResponseEntity<String>(e.getMessage(),new HttpHeaders(),HttpStatus.OK);
		}
		return response;
	}
}

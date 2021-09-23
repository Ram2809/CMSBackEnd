//package com.curriculum.repository.impl;
//
//import javax.transaction.Transactional;
//
//import org.apache.log4j.Logger;
//import org.hibernate.HibernateException;
//import org.hibernate.Session;
//import org.hibernate.SessionFactory;
//import org.hibernate.query.Query;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Repository;
//
//import com.curriculum.entity.Login;
//import com.curriculum.entity.Teacher;
//import com.curriculum.exception.ConstraintValidationException;
//import com.curriculum.exception.DatabaseException;
//import com.curriculum.exception.TeacherNotFoundException;
//import com.curriculum.repository.LoginRepository;
//@Repository
//@Transactional
//public class LoginRepositoryImpl implements LoginRepository{
//	@Autowired
//	private SessionFactory sessionFactory;
//	@Autowired
//	private TeacherRepositoryImpl teacherRepositoryImpl;
//	private Logger logger=Logger.getLogger(LoginRepositoryImpl.class);
//	@Override
//	public Login addLogin(Login loginDetails) throws DatabaseException {
//		logger.info("Adding login credentials");
//		Login response=null;
//		Session session=null;
//		Long teacherId=loginDetails.getTeacher().getId();
//		Long count=0l;
//		System.out.println(teacherId);
//		try
//		{
//			boolean teacherStatus=teacherRepositoryImpl.checkTeacher(teacherId);
//			session=sessionFactory.getCurrentSession();
//			Teacher teacher=new Teacher();
//			teacher.setId(teacherId);
//			Login login=new Login();
//			login.setLoginId(loginDetails.getLoginId());
//			login.setTeacher(teacher);
//			if(loginDetails.getPassword().length()<8)
//			{
//				throw new ConstraintValidationException("The Password must contains 8 characters!");
//			}
//			login.setPassword(loginDetails.getPassword());
//			count=(Long) session.save(login);
//			System.out.println(count);
//			if(count>0)
//			{
//				System.out.println(login);
//				response=login;
//				logger.info("Login credentials added successfully!");
//			}
//		}
//		catch( HibernateException | TeacherNotFoundException | ConstraintValidationException e)
//		{
//			logger.error("Error while adding login credentials");
//			throw new DatabaseException(e.getMessage());
//		}
//		return response;
//	}
//	@Override
//	public Login getLogin(Long teacherId) throws DatabaseException {
//		logger.info("Fetching login credentials");
//		Session session=null;
//		Login loginDetails=null;
//		try
//		{
//			boolean teacherStatus=teacherRepositoryImpl.checkTeacher(teacherId);
//			session=sessionFactory.getCurrentSession();
//			Query query=session.createQuery("FROM Login WHERE teacher_id=:teacherId");
//			query.setParameter("teacherId", teacherId);
//			loginDetails=(Login) query.getSingleResult();
//			logger.info("Login Credentials Updated successfully!");
//		}
//		catch(HibernateException | TeacherNotFoundException e)
//		{
//			logger.error("Error while fetching login credentials");
//			throw new DatabaseException(e.getMessage());
//		}
//		return loginDetails;
//	}
//	@Override
//	public Login updateLogin(Long teacherId, Login loginDetails) throws DatabaseException {
//		logger.info("Updating login credentials");
//		Session session=null;
//		Long count=0l;
//		Login response=null;
//		Long loginId=0l;
//		try
//		{
//			boolean teacherStatus=teacherRepositoryImpl.checkTeacher(teacherId);
//			session=sessionFactory.getCurrentSession();
//			String password=loginDetails.getPassword();
//			if(password.length()<8)
//			{
//				throw new ConstraintValidationException("Password must contain atleast 8 characters!");
//			}
//			else {
//				Query query=session.createQuery("UPDATE Login SET password=:password WHERE teacher_id=:teacherId");
//				query.setParameter("password", password);
//				query.setParameter("teacherId", teacherId);
//				count=(long) query.executeUpdate();
//				loginId=getLogin(teacherId).getLoginId();
//				System.out.println(loginId);
//			}
//			if(count>0)
//			{
//				response=session.get(Login.class, loginId);
//				response.setPassword(loginDetails.getPassword());
//			}
//			logger.info("Password updated successfully!");
//		}
//		catch( HibernateException | TeacherNotFoundException | ConstraintValidationException e)
//		{
//			logger.error("Error while updating login credentials");
//			throw new DatabaseException(e.getMessage());
//		}
//		return response;
//	}
//}

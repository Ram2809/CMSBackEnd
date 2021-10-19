package com.curriculum.repository.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.Login;
import com.curriculum.entity.LoginEntity;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.LoginRepository;
import com.curriculum.repository.TeacherRepository;
import com.curriculum.util.LoginMapper;
import com.curriculum.util.MailSenderUtil;

@Repository
@Transactional
public class LoginRepositoryImpl implements LoginRepository {
	@Autowired
	private SessionFactory sessionFactory;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private JavaMailSender javaMailSender;
	private Logger logger = Logger.getLogger(LoginRepositoryImpl.class);

	@Override
	public Long addLogin(Login login) throws DatabaseException, NotFoundException {
		logger.info("Adding login credentials for staff...");
		Session session = null;
		Long loginId = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			loginId = (Long) session.save(LoginMapper.loginMapper(login));
			if (loginId > 0) {
				TeacherEntity teacherEntity=teacherRepository.getParticularTeacher(login.getTeacher().getId());
				MailSenderUtil.sendMail(javaMailSender, teacherEntity.getEmail(), "Regarding account creation", "Hi"+" "+teacherEntity.getFirstName()+" "+teacherEntity.getLastName()+" "+"You have successfully created your account in our organization!\n"+" "+"Your temporary password is:"+" "+login.getPassword());
				logger.info("Login credentials are added successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding login credentials");
			throw new DatabaseException(e.getMessage());
		}
		return loginId;
	}

	@Override
	public LoginEntity getLogin(Long teacherId) throws DatabaseException {
		logger.info("Fetching login credentials...");
		Session session = null;
		LoginEntity loginEntity = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query<LoginEntity> query = session.createQuery("FROM LoginEntity WHERE teacher_id=:teacherId");
			query.setParameter("teacherId", teacherId);
			loginEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Login Credentials are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the login credentials");
			throw new DatabaseException(e.getMessage());
		}
		return loginEntity;
	}

	@Override
	public LoginEntity updateLogin(Long teacherId, Login login) throws DatabaseException {
		logger.info("Updating login credentials...");
		Session session = null;
		Long count = 0l;
		LoginEntity loginEntity = null;
		Long loginId = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			Query<Integer> query = session
					.createQuery("UPDATE LoginEntity SET password=:password WHERE teacher_id=:teacherId");
			query.setParameter("password", login.getPassword());
			query.setParameter("teacherId", teacherId);
			count = (long) query.executeUpdate();
			loginId = getLogin(teacherId).getLoginId();
			if (count > 0) {
				loginEntity = session.get(LoginEntity.class, loginId);
				loginEntity.setPassword(login.getPassword());
			}
			logger.info("Password updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating login credentials!");
			throw new DatabaseException(e.getMessage());
		}
		return loginEntity;
	}
}

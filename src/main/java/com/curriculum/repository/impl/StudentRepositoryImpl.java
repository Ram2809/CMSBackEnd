package com.curriculum.repository.impl;

import java.util.List;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.Student;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.StudentEntity;
import com.curriculum.repository.StudentRepository;
import com.curriculum.util.StudentMapper;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.StudentNotFoundException;

@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {
	@Autowired
	private ClassRepositoryImpl classRepositoryImpl;
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(StudentRepositoryImpl.class);

	@Override
	public Long addStudent(Student student) throws DatabaseException {
		logger.info("Adding student details");
		Session session = null;
		Long rollNo = null;
		try {
			session = sessionFactory.getCurrentSession();
			rollNo = (Long) session.save(StudentMapper.studentMapper(student));
			if (rollNo > 0) {
				logger.info("Student details added successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding student details!");
			throw new DatabaseException(e.getMessage());
		}
		return rollNo;
	}

	public void checkStudent(Long rollNo) throws StudentNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<StudentEntity> query = session.createQuery("FROM StudentEntity WHERE rollNo=:regNo");
		query.setParameter("regNo", rollNo);
		StudentEntity student = query.uniqueResultOptional().orElse(null);
		if (student == null) {
			throw new StudentNotFoundException("Student Not Found With" + " " + rollNo + "!");
		}
	}

	@Override
	public StudentEntity deleteStudent(Long rollNo) throws DatabaseException, NotFoundException {
		logger.info("Deleting the Student details!");
		Session session = null;
		StudentEntity studentDetail = null;
		try {
			checkStudent(rollNo);
			session = sessionFactory.getCurrentSession();
			session.find(StudentEntity.class, rollNo);
			StudentEntity studentEntity = session.load(StudentEntity.class, rollNo);
			session.delete(studentEntity);
			StudentEntity student = session.get(StudentEntity.class, rollNo);
			if (student == null) {
				studentDetail = studentEntity;
				logger.info("Student is deleted successfully!");
			} else {
				logger.error("Error while deleting the student!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the student!");
			throw new DatabaseException(e.getMessage());
		}
		return studentDetail;
	}

	@Override
	public StudentEntity getStudent(Long rollNo) throws NotFoundException, DatabaseException {
		StudentEntity studentEntity = null;
		Session session = null;
		try {
			checkStudent(rollNo);
			session = sessionFactory.getCurrentSession();
			Query<StudentEntity> query = session.createQuery("FROM StudentEntity Where rollNo=:rollNo");
			query.setParameter("rollNo", rollNo);
			studentEntity = query.uniqueResultOptional().orElse(null);
		} catch (HibernateException e) {
			logger.error("Error while fetching the student details!");
			throw new DatabaseException(e.getMessage());
		}
		return studentEntity;
	}

	@Override
	public List<StudentEntity> getStudentByClass(Long roomNo) throws DatabaseException {
		logger.info("Getting student details by class!");
		Session session = null;
		List<StudentEntity> studentsList = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query<StudentEntity> query = session.createQuery("FROM StudentEntity WHERE roomNo=:roomId");
			query.setParameter("roomId", roomNo);
			studentsList = query.list();
			logger.info("Student details fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the student details!");
			throw new DatabaseException(e.getMessage());
		}
		return studentsList;
	}

	@Override
	public StudentEntity updateStudent(Long rollNo, Student student) throws DatabaseException {
		logger.info("Updating student details...");
		StudentEntity studentEntity = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			StudentEntity studentDetail = StudentMapper.studentMapper(student);
			session.find(StudentEntity.class, rollNo);
			StudentEntity updatedStudentEntity = session.load(StudentEntity.class, rollNo);
			updatedStudentEntity.setFirstName(studentDetail.getFirstName());
			updatedStudentEntity.setLastName(studentDetail.getLastName());
			updatedStudentEntity.setDateOfBirth(studentDetail.getDateOfBirth());
			updatedStudentEntity.setGender(studentDetail.getGender());
			updatedStudentEntity.setContactNo(studentDetail.getContactNo());
			updatedStudentEntity.setAddress(studentDetail.getAddress());
			ClassEntity classEntity = new ClassEntity();
			classEntity.setRoomNo(student.getClassDetail().getRoomNo());
			updatedStudentEntity.setClassDetail(classEntity);
			studentEntity = (StudentEntity) session.merge(updatedStudentEntity);
			logger.info("Student details is updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the student details!");
			throw new DatabaseException(e.getMessage());
		}
		return studentEntity;
	}

}

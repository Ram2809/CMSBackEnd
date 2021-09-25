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
				System.out.println(student);
			}
		} catch (HibernateException e) {
			logger.error("Error while adding student details!");
			throw new DatabaseException(e.getMessage());
		}
		return rollNo;
	}

//	@Override
//	public ResponseEntity<List<Student>> getAllStudentDetails() {
//		// TODO Auto-generated method stub
//		ResponseEntity<List<Student>> response = null;
//		List<Student> studentDetailsList = new ArrayList();
//		Session session = null;
//		try {
//			session = sessionFactory.getCurrentSession();
//			Query<Student> query = session.createQuery("FROM Student s");
//			studentDetailsList = query.list();
//			response = new ResponseEntity<List<Student>>(studentDetailsList, new HttpHeaders(), HttpStatus.OK);
//		} catch (HibernateException e) {
//			e.printStackTrace();
//		} 
//		return response;
//	}
//
	public void checkStudent(Long rollNo) throws StudentNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<StudentEntity> query = session.createQuery("FROM StudentEntity WHERE rollNo=:regNo");
		query.setParameter("regNo", rollNo);
		StudentEntity student = query.uniqueResultOptional().orElse(null);
		if (student == null) {
			throw new StudentNotFoundException("Student Not Found With" + " " + rollNo + "!");
		}
	}

//	@Override
//	public ResponseEntity<String> updateStudentDetails(Long rollNo, Student studentDetails)
//			throws StudentNotFoundException {
//		// TODO Auto-generated method stub
//		ResponseEntity<String> response = null;
//		Session session = null;
//		try {
//			boolean checkStudent = checkStudent(rollNo);
//			if (!checkStudent) {
//				throw new StudentNotFoundException("Student Not Found with" + " " + rollNo + "!");
//			}
//			session = sessionFactory.getCurrentSession();
//			//session.beginTransaction();
//			session.find(Student.class, rollNo);
//			Student newStudentDetails = session.load(Student.class, rollNo);
//			newStudentDetails.setFirstName(studentDetails.getFirstName());
//			newStudentDetails.setLastName(studentDetails.getLastName());
//			newStudentDetails.setDateOfBirth(studentDetails.getDateOfBirth());
//			newStudentDetails.setGender(studentDetails.getGender());
//			newStudentDetails.setContactNo(studentDetails.getContactNo());
//			newStudentDetails.setAddress(studentDetails.getAddress());
//			session.merge(newStudentDetails);
//			//session.flush();
//			//session.getTransaction().commit();
//			response = new ResponseEntity<String>("Student Details Updated Successfully!", new HttpHeaders(),
//					HttpStatus.OK);
//		} catch (HibernateException | StudentNotFoundException e) {
//			response = new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.OK);
//		}		
//		return response;
//	}

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

//	@Override
//	public ResponseEntity<Student> getParticularStudentDetails(Long rollNo) throws StudentNotFoundException {
//		// TODO Auto-generated method stub
//		ResponseEntity<Student> response = null;
//		Session session = null;
//		try {
//			boolean checkStudent = checkStudent(rollNo);
//			if (!checkStudent) {
//				throw new StudentNotFoundException("Student Not Found with" + " " + rollNo + "!");
//			}
//			session = sessionFactory.getCurrentSession();
//			Query<Student> query = session.createQuery("FROM Student Where rollNo=:rollNo");
//			query.setParameter("rollNo", rollNo);
//			Student studentDetails = (Student) query.getSingleResult();
//			response = new ResponseEntity<Student>(studentDetails, new HttpHeaders(), HttpStatus.OK);
//		} catch (HibernateException | StudentNotFoundException e) {
//			e.printStackTrace();
//		} 
//		return response;
//	}

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

}

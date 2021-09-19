package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;
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

import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.Student;
import com.curriculum.repository.StudentRepository;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.StudentNotFoundException;

@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {
	@Autowired
	private ClassRepositoryImpl classRepositoryImpl;
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger=Logger.getLogger(StudentRepositoryImpl.class);
	@Override
	public Student addStudent(Student studentDetails) throws DatabaseException {
		logger.info("Adding student details");
		Session session = null;
		Student student = null;
		try {
			boolean checkStatus = classRepositoryImpl.checkClassRoom(studentDetails.getClassEntity().getRoomNo());
			session = sessionFactory.getCurrentSession();
			ClassEntity classEntity = new ClassEntity();
			classEntity.setRoomNo(studentDetails.getClassEntity().getRoomNo());
			Student studentEntity = new Student();
			studentEntity.setRollNo(studentDetails.getRollNo());
			studentEntity.setFirstName(studentDetails.getFirstName());
			studentEntity.setLastName(studentDetails.getLastName());
			studentEntity.setDateOfBirth(studentDetails.getDateOfBirth());
			studentEntity.setGender(studentDetails.getGender());
			studentEntity.setContactNo(studentDetails.getContactNo());
			studentEntity.setAddress(studentDetails.getAddress());
			studentEntity.setClassEntity(classEntity);
			Long count=(Long) session.save(studentEntity);
			System.out.println(count);
			if(count>0)
			{
				logger.info("Student details added successfully!");
				student=studentEntity;
				System.out.println(student);
			}
		} catch (HibernateException | ClassNotFoundException e) {
			logger.error("Error while adding student details!");
			throw new DatabaseException(e.getMessage());
		} 
		return student;
	}

	@Override
	public ResponseEntity<List<Student>> getAllStudentDetails() {
		// TODO Auto-generated method stub
		ResponseEntity<List<Student>> response = null;
		List<Student> studentDetailsList = new ArrayList();
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query<Student> query = session.createQuery("FROM Student s");
			studentDetailsList = query.list();
			response = new ResponseEntity<List<Student>>(studentDetailsList, new HttpHeaders(), HttpStatus.OK);
		} catch (HibernateException e) {
			e.printStackTrace();
		} 
		return response;
	}

	public boolean checkStudent(Long rollNo) throws StudentNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query query = session.createQuery("FROM Student WHERE rollNo=:regNo");
		query.setParameter("regNo", rollNo);
		Student student = (Student) query.uniqueResultOptional().orElse(null);
		if (student==null) {
			throw new StudentNotFoundException("Student Not Found With"+" "+rollNo+"!");
		}
		return true;
	}

	@Override
	public ResponseEntity<String> updateStudentDetails(Long rollNo, Student studentDetails)
			throws StudentNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<String> response = null;
		Session session = null;
		try {
			boolean checkStudent = checkStudent(rollNo);
			if (!checkStudent) {
				throw new StudentNotFoundException("Student Not Found with" + " " + rollNo + "!");
			}
			session = sessionFactory.getCurrentSession();
			//session.beginTransaction();
			session.find(Student.class, rollNo);
			Student newStudentDetails = session.load(Student.class, rollNo);
			newStudentDetails.setFirstName(studentDetails.getFirstName());
			newStudentDetails.setLastName(studentDetails.getLastName());
			newStudentDetails.setDateOfBirth(studentDetails.getDateOfBirth());
			newStudentDetails.setGender(studentDetails.getGender());
			newStudentDetails.setContactNo(studentDetails.getContactNo());
			newStudentDetails.setAddress(studentDetails.getAddress());
			session.merge(newStudentDetails);
			//session.flush();
			//session.getTransaction().commit();
			response = new ResponseEntity<String>("Student Details Updated Successfully!", new HttpHeaders(),
					HttpStatus.OK);
		} catch (HibernateException | StudentNotFoundException e) {
			response = new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.OK);
		}		
		return response;
	}

	@Override
	public Student deleteStudent(Long rollNo) throws DatabaseException  {
		logger.info("Deleting the Student details!");
		Session session = null;
		Student response=null;
		try {
			boolean checkStudent = checkStudent(rollNo);
			session = sessionFactory.getCurrentSession();
			session.find(Student.class, rollNo);
			Student studentEntity = session.load(Student.class, rollNo);
			session.delete(studentEntity);
			Student student=session.get(Student.class, rollNo);
			if(student==null)
			{
				response=studentEntity;
				logger.info("Student is deleted successfully!");
			}
			else
			{
				logger.error("Error while deleting the student!");
			}
		} catch (HibernateException |StudentNotFoundException e) {
			logger.error("Error while deleting the student!");
			throw new DatabaseException(e.getMessage());
		}
		return response;
	}

	@Override
	public ResponseEntity<Student> getParticularStudentDetails(Long rollNo) throws StudentNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<Student> response = null;
		Session session = null;
		try {
			boolean checkStudent = checkStudent(rollNo);
			if (!checkStudent) {
				throw new StudentNotFoundException("Student Not Found with" + " " + rollNo + "!");
			}
			session = sessionFactory.getCurrentSession();
			Query<Student> query = session.createQuery("FROM Student Where rollNo=:rollNo");
			query.setParameter("rollNo", rollNo);
			Student studentDetails = (Student) query.getSingleResult();
			response = new ResponseEntity<Student>(studentDetails, new HttpHeaders(), HttpStatus.OK);
		} catch (HibernateException | StudentNotFoundException e) {
			e.printStackTrace();
		} 
		return response;
	}

	@Override
	public List<Student> getStudentByClass(Long roomNo) throws DatabaseException {
		logger.info("Getting student details by class!");
		Session session=null;
		List<Student> studentsList=null;
		try
		{
			boolean checkStatus = classRepositoryImpl.checkClassRoom(roomNo);
			session=sessionFactory.getCurrentSession();
			Query<Student> query=session.createQuery("FROM Student WHERE roomNo=:roomId");
			query.setParameter("roomId", roomNo);
			studentsList=query.list();
			logger.info("Student details fetched successfully!");
		}
		catch(HibernateException | ClassNotFoundException e)
		{
			logger.error("Error while fetching the student details!");
			throw new DatabaseException(e.getMessage());
		}
		return studentsList;
	}

}
;
package com.curriculum.repository.impl;

import java.util.ArrayList;
import java.util.List;
import javax.transaction.Transactional;

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
import com.curriculum.exception.ClassNotFoundException;
import com.curriculum.exception.StudentNotFoundException;

@Repository
@Transactional
public class StudentRepositoryImpl implements StudentRepository {
	@Autowired
	private ClassRepositoryImpl classRepositoryImpl;
	@Autowired
	private SessionFactory sessionFactory;

	@Override
	public ResponseEntity<String> addStudentDetails(Long roomNo, Student studentDetails) {
		// TODO Auto-generated method stub
		Session session = null;
		ResponseEntity<String> response = null;
		try {
			boolean checkStatus = classRepositoryImpl.checkClassRoomNo(roomNo);
			if (!checkStatus) {
				throw new ClassNotFoundException("Class Not Found with" + " " + roomNo + "!");
			}
			session = sessionFactory.openSession();
			session.beginTransaction();
			ClassEntity classEntity = new ClassEntity();
			classEntity.setRoomNo(roomNo);
			// Set<Student> studentSet=new HashSet<Student>();
			Student studentEntity = new Student();
			studentEntity.setRollNo(studentDetails.getRollNo());
			studentEntity.setFirstName(studentDetails.getFirstName());
			studentEntity.setLastName(studentDetails.getLastName());
			studentEntity.setDateOfBirth(studentDetails.getDateOfBirth());
			studentEntity.setGender(studentDetails.getGender());
			studentEntity.setContactNo(studentDetails.getContactNo());
			studentEntity.setAddress(studentDetails.getAddress());
			studentEntity.setClassEntity(classEntity);
			session.save(studentEntity);
			session.getTransaction().commit();
			response = new ResponseEntity<String>("Student Details Added Successfully!", new HttpHeaders(),
					HttpStatus.OK);
		} catch (HibernateException | ClassNotFoundException e) {
			response = new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.OK);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return response;
	}

	@Override
	public ResponseEntity<List<Student>> getAllStudentDetails() {
		// TODO Auto-generated method stub
		ResponseEntity<List<Student>> response = null;
		List<Student> studentDetailsList = new ArrayList();
		Session session = null;
		try {
			session = sessionFactory.openSession();
			Query query = session.createQuery("FROM Student s");
			studentDetailsList = query.list();
			response = new ResponseEntity<List<Student>>(studentDetailsList, new HttpHeaders(), HttpStatus.OK);
		} catch (HibernateException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return response;
	}

	public boolean checkStudent(Long rollNo) {
		Session session = sessionFactory.openSession();
		Query query = session.createQuery("FROM Student WHERE rollNo=:regNo");
		query.setParameter("regNo", rollNo);
		List<Student> studentList = query.list();
		if (studentList.isEmpty()) {
			return false;
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
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.find(Student.class, rollNo);
			Student newStudentDetails = session.load(Student.class, rollNo);
			newStudentDetails.setFirstName(studentDetails.getFirstName());
			newStudentDetails.setLastName(studentDetails.getLastName());
			newStudentDetails.setDateOfBirth(studentDetails.getDateOfBirth());
			newStudentDetails.setGender(studentDetails.getGender());
			newStudentDetails.setContactNo(studentDetails.getContactNo());
			newStudentDetails.setAddress(studentDetails.getAddress());
			session.merge(newStudentDetails);
			session.flush();
			session.getTransaction().commit();
			response = new ResponseEntity<String>("Student Details Updated Successfully!", new HttpHeaders(),
					HttpStatus.OK);
		} catch (HibernateException | StudentNotFoundException e) {
			response = new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.OK);
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return response;
	}

	@Override
	public ResponseEntity<String> deleteStudentDetails(Long rollNo) throws StudentNotFoundException {
		// TODO Auto-generated method stub
		ResponseEntity<String> response = null;
		Session session = null;
		try {
			boolean checkStudent = checkStudent(rollNo);
			if (!checkStudent) {
				throw new StudentNotFoundException("Student Not Found with" + " " + rollNo + "!");
			}
			session = sessionFactory.openSession();
			session.beginTransaction();
			session.find(Student.class, rollNo);
			Student studentEntity = session.load(Student.class, rollNo);
			session.delete(studentEntity);
			session.flush();
			session.getTransaction().commit();
			response = new ResponseEntity<String>("Student Details Deleted Successfully!", new HttpHeaders(),
					HttpStatus.OK);
		} catch (HibernateException e) {
			response = new ResponseEntity<String>(e.getMessage(), new HttpHeaders(), HttpStatus.OK);
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
			session = sessionFactory.openSession();
			Query query = session.createQuery("FROM Student Where rollNo=:rollNo");
			query.setParameter("rollNo", rollNo);
			Student studentDetails = (Student) query.getSingleResult();
			response = new ResponseEntity<Student>(studentDetails, new HttpHeaders(), HttpStatus.OK);
		} catch (HibernateException | StudentNotFoundException e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return response;
	}

	@Override
	public ResponseEntity<List<Student>> getStudentByClass(Long roomNo) {
		// TODO Auto-generated method stub
		ResponseEntity<List<Student>> response=null;
		Session session=null;
		List<Student> studentsList=null;
		try
		{
			boolean checkStatus = classRepositoryImpl.checkClassRoomNo(roomNo);
			if (!checkStatus) {
				throw new ClassNotFoundException("Class Not Found with" + " " + roomNo + "!");
			}
			session=sessionFactory.openSession();
			Query query=session.createQuery("FROM Student WHERE roomNo=:roomId");
			query.setParameter("roomId", roomNo);
			studentsList=query.list();
			response=new ResponseEntity<List<Student>>(studentsList,new HttpHeaders(),HttpStatus.OK);
		}
		catch(HibernateException | ClassNotFoundException e)
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
;
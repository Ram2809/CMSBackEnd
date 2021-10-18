package com.curriculum.repository.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.StudentAssignDTO;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.StudentAssign;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;
import com.curriculum.repository.StudentAssignRepository;
import com.curriculum.util.StudentAssignMapper;

@Repository
@Transactional
public class StudentAssignRepositoryImpl implements StudentAssignRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(StudentAssignRepositoryImpl.class);

	@Override
	public Long addStudentAssign(StudentAssignDTO studentAssignDTO) throws DatabaseException, NotAllowedException {
		Long assignId=0l;
		StudentAssign studentAssignEntity = checkStudentAssignByAcademicYear(studentAssignDTO.getStudent().getRollNo(),
				studentAssignDTO.getAcademicYear());
		List<StudentAssign> studentAssignList = checkStudentAssignByRollNo(studentAssignDTO.getStudent().getRollNo());
		if (studentAssignEntity != null) {
			StudentAssign updatedStudentAssign=StudentAssignMapper.mapStudentAssign(studentAssignDTO);
			StudentAssign studentAssign = updateStudentAssign(studentAssignEntity.getId(), updatedStudentAssign);
			assignId = studentAssign.getId();
		}
		else if (!studentAssignList.isEmpty()) {
			StudentAssign studentAssign = studentAssignList.get(0);
			studentAssign.setStudentLeftOn(LocalDate.now());
			updateStudentAssign(studentAssign.getId(), studentAssign);
			assignId=saveStudentAssign(studentAssignDTO);
		}
		else {
			assignId=saveStudentAssign(studentAssignDTO);
		}
		return assignId;
	}

	public StudentAssign checkStudentAssignByAcademicYear(Long rollNo, String academicYear) {
		StudentAssign studentAssign = null;
		Session session = sessionFactory.getCurrentSession();
		Query<StudentAssign> query = session
				.createQuery("FROM StudentAssign WHERE student.rollNo=:rollNo AND academicYear=:academicYear");
		query.setParameter("rollNo", rollNo);
		query.setParameter("academicYear", academicYear);
		studentAssign = query.uniqueResultOptional().orElse(null);
		return studentAssign;
	}

	public List<StudentAssign> checkStudentAssignByRollNo(Long rollNo) {
		List<StudentAssign> studentAssignList = new ArrayList<>();
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
			CriteriaQuery<StudentAssign> criteriaQuery = criteriaBuilder.createQuery(StudentAssign.class);
			Root<StudentAssign> root = criteriaQuery.from(StudentAssign.class);
			criteriaQuery.orderBy(criteriaBuilder.desc(root.get("academicYear")));
			criteriaQuery.where(criteriaBuilder.equal(root.get("student").get("rollNo"), rollNo));
			Query<StudentAssign> query = session.createQuery(criteriaQuery);
			studentAssignList = query.getResultList();
		} catch (HibernateException e) {
			logger.error("");
		}
		return studentAssignList;
	}

	public StudentAssign updateStudentAssign(Long assignId, StudentAssign studentAssignDTO) throws DatabaseException {
		logger.info("Updating the student assign details...");
		Session session = null;
		StudentAssign studentAssign = null;
		try {
			session = sessionFactory.getCurrentSession();
			session.find(StudentAssign.class, assignId);
			StudentAssign updatedStudentAssign = session.load(StudentAssign.class, assignId);
			updatedStudentAssign.setAcademicYear(studentAssignDTO.getAcademicYear());
			updatedStudentAssign.setStudentAddedOn(studentAssignDTO.getStudentAddedOn());
			updatedStudentAssign.setStudentLeftOn(studentAssignDTO.getStudentLeftOn());
			ClassEntity classEntity = new ClassEntity();
			classEntity.setRoomNo(studentAssignDTO.getClassDetail().getRoomNo());
			updatedStudentAssign.setClassDetail(classEntity);
			studentAssign = (StudentAssign) session.merge(updatedStudentAssign);
			if(studentAssign!=null)
			{
				logger.info("Student assign details updated successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while updating the student assign details!");
			throw new DatabaseException(e.getMessage());
		}
		return studentAssign;
	}

	public long saveStudentAssign(StudentAssignDTO studentAssignDTO) throws DatabaseException {
		logger.info("Assigning tudent to particular class...");
		Session session = null;
		Long assignId = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			assignId = (Long) session.save(StudentAssignMapper.mapStudentAssign(studentAssignDTO));
			if (assignId > 0) {
				logger.info("Student assigned for class successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while assigning student to particular class!");
			throw new DatabaseException(e.getMessage());
		}
		return assignId;
	}

	@Override
	public List<StudentAssign> getStudentClassDetails(Long roomNo, String academicYear) throws DatabaseException {
		logger.info("Fetching the student class details...");
		Session session=null;
		List<StudentAssign> studentAssignList=new ArrayList<>();
		try {
			session=sessionFactory.getCurrentSession();
			Query<StudentAssign> query=session.createQuery("FROM StudentAssign WHERE classDetail.roomNo=:roomNo AND academicYear=:academicYear");
			query.setParameter("roomNo", roomNo);
			query.setParameter("academicYear", academicYear);
			studentAssignList=query.getResultList();
			logger.info("Student class details are fetched successfully!");
		}
		catch(HibernateException e) {
			logger.error("Error while fetching student class details!");
			throw new DatabaseException(e.getMessage());
		}
		return studentAssignList;
	}

	@Override
	public StudentAssign updateStudentAssign(Long assignId, StudentAssignDTO studentAssignDTO) throws DatabaseException {
		logger.info("Updating the student assign details...");
		Session session = null;
		StudentAssign studentAssign = null;
		try {
			session = sessionFactory.getCurrentSession();
			StudentAssign studentAssignEntity=StudentAssignMapper.mapStudentAssign(studentAssignDTO);
			session.find(StudentAssign.class, assignId);
			StudentAssign updatedStudentAssign = session.load(StudentAssign.class, assignId);
			updatedStudentAssign.setAcademicYear(studentAssignEntity.getAcademicYear());
			updatedStudentAssign.setStudentAddedOn(studentAssignEntity.getStudentAddedOn());
			updatedStudentAssign.setStudentLeftOn(studentAssignEntity.getStudentLeftOn());
			ClassEntity classEntity = new ClassEntity();
			classEntity.setRoomNo(studentAssignEntity.getClassDetail().getRoomNo());
			updatedStudentAssign.setClassDetail(classEntity);
			studentAssign = (StudentAssign) session.merge(updatedStudentAssign);
			if(studentAssign!=null)
			{
				logger.info("Student assign details updated successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while updating the student assign details!");
			throw new DatabaseException(e.getMessage());
		}
		return studentAssign;
	}
}

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
import org.springframework.stereotype.Repository;
import com.curriculum.dto.Class;
import com.curriculum.entity.ClassEntity;
import com.curriculum.repository.ClassRepository;
import com.curriculum.util.ClassMapper;
import com.curriculum.exception.*;
import com.curriculum.exception.ClassNotFoundException;

@Repository
@Transactional
public class ClassRepositoryImpl implements ClassRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(ClassRepositoryImpl.class);

	@Override
	public Long addClass(Class classDetail) throws DatabaseException, NotAllowedException {
		logger.info("Adding class details...");
		Session session = null;
		Long roomNo = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			Query<ClassEntity> query = session
					.createQuery("FROM ClassEntity c WHERE c.standard=:standard AND c.section=:section");
			query.setParameter("standard", classDetail.getStandard());
			query.setParameter("section", classDetail.getSection());
			ClassEntity classEntity = query.uniqueResultOptional().orElse(null);
			if (classEntity != null) {
				throw new NotAllowedException("Standard and section already exits!");
			}
			roomNo = (Long) session.save(ClassMapper.mapClass(classDetail));
			if (roomNo > 0) {
				logger.info("Class details are added successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding the class!");
			throw new DatabaseException(e.getMessage());
		}
		return roomNo;
	}

	@Override
	public List<ClassEntity> getAllClass() throws DatabaseException {
		logger.info("Getting all class details...");
		Session session = null;
		List<ClassEntity> classList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query query = session.createQuery("FROM ClassEntity c");
			classList = query.list();
			logger.info("All class details are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the class details!");
			throw new DatabaseException(e.getMessage());
		}
		return classList;
	}

	@Override
	public ClassEntity updateClass(Long roomNo, Class classDetail) throws DatabaseException, NotFoundException {
		logger.info("Updating the class details...");
		Session session = null;
		ClassEntity updatedClassEntity = null;
		try {
			checkClassRoom(roomNo);
			session = sessionFactory.getCurrentSession();
			Query<ClassEntity> query = session
					.createQuery("FROM ClassEntity c WHERE c.standard=:standard AND c.section=:section");
			query.setParameter("standard", classDetail.getStandard());
			query.setParameter("section", classDetail.getSection());
			ClassEntity classDetails = query.uniqueResultOptional().orElse(null);
			if (classDetails != null) {
				throw new NotAllowedException("Standard and section already exits!");
			}
			ClassEntity classEntityDetail = ClassMapper.mapClass(classDetail);
			session.find(ClassEntity.class, roomNo);
			ClassEntity classEntity = session.load(ClassEntity.class, roomNo);
			classEntity.setStandard(classEntityDetail.getStandard());
			classEntity.setSection(classEntityDetail.getSection());
			updatedClassEntity = (ClassEntity) session.merge(classEntity);
			logger.info("Class Details are updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the class!");
			throw new DatabaseException(e.getMessage());
		}
		return updatedClassEntity;
	}

	public void checkClassRoom(Long id) throws ClassNotFoundException {
		ClassEntity classEntity = null;
		Session session = sessionFactory.getCurrentSession();
		Query<ClassEntity> query = session.createQuery("FROM ClassEntity WHERE id=:roomNo");
		query.setParameter("roomNo", id);
		classEntity = query.uniqueResultOptional().orElse(null);
		if (classEntity == null) {
			throw new ClassNotFoundException("Class Room Not Found With" + " " + id + "!");
		}
	}

	public void checkStandard(String standard) throws NotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<ClassEntity> query = session.createQuery("FROM ClassEntity WHERE standard=:standard");
		query.setParameter("standard", standard);
		List<ClassEntity> classList = query.list();
		if (classList.isEmpty()) {
			throw new NotFoundException("Standard Not Found With" + " " + standard + "!");
		}
	}

	public void checkSection(String section) throws NotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<ClassEntity> query = session.createQuery("FROM ClassEntity WHERE section=:section");
		query.setParameter("section", section);
		List<ClassEntity> classList = query.getResultList();
		if (classList.isEmpty()) {
			throw new NotFoundException("Section Not Found With" + " " + section + "!");
		}
	}

	@Override
	public ClassEntity deleteClass(Long roomNo) throws DatabaseException, NotFoundException {
		logger.info("Deleting the class details...");
		Session session = null;
		ClassEntity deletedClassEntity = null;
		try {
			checkClassRoom(roomNo);
			session = sessionFactory.getCurrentSession();
			session.find(ClassEntity.class, roomNo);
			ClassEntity classDetail = session.load(ClassEntity.class, roomNo);
			session.delete(classDetail);
			ClassEntity classEntity = session.get(ClassEntity.class, roomNo);
			if (classEntity == null) {
				deletedClassEntity = classDetail;
				logger.info("Class Details deleted successfully!");
			} else {
				logger.error("Error while deleting the class!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the class!");
			throw new DatabaseException(e.getMessage());
		}
		return deletedClassEntity;
	}

	@Override
	public ClassEntity getParticularClass(Long roomNo) throws DatabaseException, NotFoundException {
		logger.info("Getting particular class details...");
		Session session = null;
		ClassEntity classDetail = new ClassEntity();
		try {
			checkClassRoom(roomNo);
			session = sessionFactory.getCurrentSession();
			Query<ClassEntity> query = session.createQuery("FROM ClassEntity WHERE roomNo=:roomId");
			query.setParameter("roomId", roomNo);
			classDetail = query.getSingleResult();
			logger.info("Particular class details fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the particular class details!");
			throw new DatabaseException(e.getMessage());
		}
		return classDetail;
	}

	@Override
	public List<ClassEntity> getClassList(String standard) throws DatabaseException, NotFoundException {
		logger.info("Getting class rooms for particular standard...");
		Session session = null;
		List<ClassEntity> classList = null;
		try {
			checkStandard(standard);
			session = sessionFactory.getCurrentSession();
			Query<ClassEntity> query = session.createQuery("FROM ClassEntity c where c.standard=:standard");
			query.setParameter("standard", standard);
			classList = query.getResultList();
			logger.info("class details are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the class details!");
			throw new DatabaseException(e.getMessage());
		}
		return classList;
	}

	@Override
	public Long getClassRoomNo(String standard, String section) throws DatabaseException, NotFoundException {
		logger.info("Getting classroom for given standard and section...");
		Session session = null;
		Long classRoomNo = null;
		try {
			checkStandard(standard);
			checkSection(section);
			session = sessionFactory.getCurrentSession();
			Query<Long> query = session.createQuery(
					"SELECT c.roomNo FROM ClassEntity c WHERE c.standard=:standard AND c.section=:section");
			query.setParameter("standard", standard);
			query.setParameter("section", section);
			classRoomNo = query.uniqueResultOptional().orElse(null);
			logger.info("Classroom is fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the class room!");
			throw new DatabaseException(e.getMessage());
		}
		return classRoomNo;
	}

	@Override
	public List<ClassEntity> getClassList(List<Long> roomNoList) throws DatabaseException, NotFoundException {
		logger.info("Getting Class Details...");
		List<ClassEntity> classList = new ArrayList<>();
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			for (Long roomNo : roomNoList) {
				checkClassRoom(roomNo);
				Query<ClassEntity> query = session.createQuery("FROM ClassEntity WHERE roomNo=:roomNo");
				query.setParameter("roomNo", roomNo);
				classList.add(query.getSingleResult());
			}
			if (!classList.isEmpty()) {
				logger.info("Class Details are fetched successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error occured while getting class details!");
			throw new DatabaseException(e.getMessage());
		}
		return classList;
	}

}

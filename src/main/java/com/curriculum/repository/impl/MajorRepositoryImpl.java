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

import com.curriculum.dto.Major;
import com.curriculum.entity.MajorEntity;
import com.curriculum.entity.StudentEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.StudentNotFoundException;
import com.curriculum.repository.MajorRepository;
import com.curriculum.util.MajorMapper;
@Repository
@Transactional
public class MajorRepositoryImpl implements MajorRepository{
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger=Logger.getLogger(MajorRepositoryImpl.class);
	@Override
	public Long addMajor(Major major) throws DatabaseException {
		logger.info("Adding major details...");
		Session session=null;
		Long majorId=0l;
		try {
			session=sessionFactory.getCurrentSession();
			majorId=(Long) session.save(MajorMapper.mapMajor(major));
			if(majorId>0) {
				logger.info("Major details added successfully!");
			}
		}
		catch(HibernateException e) {
			logger.error("Error while adding the major details!");
			throw new DatabaseException(e.getMessage());
		}
		return majorId;
	}
	@Override
	public List<MajorEntity> getMajors() throws DatabaseException {
		Session session=null;
		List<MajorEntity> majorList=new ArrayList<>();
		try {
			session=sessionFactory.getCurrentSession();
			Query<MajorEntity> query=session.createQuery("FROM MajorEntity");
			majorList=query.getResultList();
			logger.info("Major details are fetched successfully!");
		}
		catch(HibernateException e) {
			logger.error("Error while fetching the major details!");
			throw new DatabaseException(e.getMessage());
		}
		return majorList;
	}
	public void checkMajor(Long id) throws NotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<MajorEntity> query = session.createQuery("FROM MajorEntity WHERE id=:majorId");
		query.setParameter("majorId", id);
		MajorEntity major = query.uniqueResultOptional().orElse(null);
		System.out.println(major);
		if (major == null) {
			throw new NotFoundException("Major Not Found!");
		}
	}
	
	@Override
	public MajorEntity deleteMajor(Long majorId) throws NotFoundException, DatabaseException {
		System.out.println(majorId);
		logger.info("Deleting the major detail...");
		Session session=null;
		MajorEntity majorEntity=null;
		try {
			checkMajor(majorId);
			session=sessionFactory.getCurrentSession();
			MajorEntity major=session.load(MajorEntity.class, majorId);
			session.delete(major);
			MajorEntity deletedMajor=session.get(MajorEntity.class, majorId);
			if(deletedMajor==null) {
				majorEntity=major;
				logger.info("Major deleted successfully!");
			}
		}
		catch(HibernateException e){
			logger.error("Error while deleting the major");
			throw new DatabaseException(e.getMessage());
		}
		return majorEntity;
	}

}

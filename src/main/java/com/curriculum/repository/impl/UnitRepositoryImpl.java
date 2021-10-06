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

import com.curriculum.dto.Unit;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.entity.UnitEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.UnitNotFoundException;
import com.curriculum.repository.UnitRepository;
import com.curriculum.util.UnitMapper;

@Repository
@Transactional
public class UnitRepositoryImpl implements UnitRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(UnitRepositoryImpl.class);

	@Override
	public String addUnit(Unit unit) throws DatabaseException, NotFoundException {
		logger.info("Adding the unit details!");
		Session session = null;
		String unitNo = null;
		try {
			session = sessionFactory.getCurrentSession();
			unitNo = (String) session.save(UnitMapper.mapUnit(unit));
			if (!unitNo.isEmpty()) {
				logger.info("Unit is added successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding the unit!");
			throw new DatabaseException(e.getMessage(), e);
		}
		return unitNo;
	}

	@Override
	public List<UnitEntity> getUnitBySubjectCode(String subjectCode) throws DatabaseException {
		logger.info("Getting units for given subject!");
		Session session = null;
		List<UnitEntity> unitsList = new ArrayList<>();
		try {
			session = sessionFactory.getCurrentSession();
			Query<UnitEntity> query = session.createQuery("FROM UnitEntity t WHERE t.subject.code=:subjectCode");
			query.setParameter("subjectCode", subjectCode);
			unitsList = query.getResultList();
			logger.info("Unit details are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the unit details!");
			throw new DatabaseException(e.getMessage());
		}
		return unitsList;
	}

	public void checkUnit(String unitNo) throws UnitNotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<UnitEntity> query = session.createQuery("FROM UnitEntity WHERE unitNo=:unitNo");
		query.setParameter("unitNo", unitNo);
		UnitEntity unitEntity = query.uniqueResultOptional().orElse(null);
		if (unitEntity == null) {
			throw new UnitNotFoundException("Unit not found with" + " " + unitNo + "!");
		}
	}

	@Override
	public UnitEntity getUnitByUnitNo(String unitNo) throws DatabaseException, NotFoundException {
		logger.info("Getting particular unit details");
		Session session = null;
		UnitEntity unitEntity = null;
		try {
			checkUnit(unitNo);
			session = sessionFactory.getCurrentSession();
			Query<UnitEntity> query = session.createQuery("FROM UnitEntity WHERE unitNo=:unitNo");
			query.setParameter("unitNo", unitNo);
			unitEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Unit details fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the unit details!");
			throw new DatabaseException(e.getMessage());
		}
		return unitEntity;
	}

	@Override
	public UnitEntity updateUnit(String unitNo, Unit unit) throws DatabaseException, NotFoundException {
		logger.info("Updating unit details!");
		Session session = null;
		UnitEntity unitEntity = null;
		try {
			checkUnit(unitNo);
			session = sessionFactory.getCurrentSession();
			UnitEntity unitDetail = UnitMapper.mapUnit(unit);
			session.find(UnitEntity.class, unitNo);
			UnitEntity updatedUnitEntity = session.load(UnitEntity.class, unitNo);
			updatedUnitEntity.setUnitName(unitDetail.getUnitName());
			updatedUnitEntity.setDescription(unitDetail.getDescription());
			updatedUnitEntity.setMonth(unitDetail.getMonth());
			SubjectEntity subjectEntity = new SubjectEntity();
			subjectEntity.setCode(unit.getSubject().getCode());
			updatedUnitEntity.setSubject(subjectEntity);
			unitEntity = (UnitEntity) session.merge(updatedUnitEntity);
			logger.info("Unit details updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the unit details!");
			throw new DatabaseException(e.getMessage());
		}
		return unitEntity;
	}

	@Override
	public UnitEntity deleteUnit(String unitNo) throws DatabaseException, NotFoundException {
		logger.info("Deleting the unit details!");
		Session session = null;
		UnitEntity unitEntity = null;
		try {
			checkUnit(unitNo);
			session = sessionFactory.getCurrentSession();
			session.find(UnitEntity.class, unitNo);
			UnitEntity unitDetail = session.load(UnitEntity.class, unitNo);
			session.delete(unitDetail);
			UnitEntity deletedUnitEntity = session.get(UnitEntity.class, unitNo);
			if (deletedUnitEntity == null) {
				unitEntity = unitDetail;
				logger.info("Unit is deleted successfully!");
			} else {
				logger.error("Error while deleting the unit details!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the unit details!");
			throw new DatabaseException(e.getMessage());
		}
		return unitEntity;
	}

	@Override
	public String getSubjectCode(String unitNo) throws DatabaseException, NotFoundException {
		logger.info("Getting subject by unit number!");
		Session session = null;
		String subjectCode = "";
		try {
			checkUnit(unitNo);
			session = sessionFactory.getCurrentSession();
			Query<String> query = session
					.createQuery("SELECT t.subject.code FROM UnitEntity t WHERE t.unitNo=:unitNo");
			query.setParameter("unitNo", unitNo);
			subjectCode = query.uniqueResult();
			logger.info("Subject code fetched successfully using unit number!");
		} catch (HibernateException e) {
			logger.error("Error while fetching the subject code!");
			throw new DatabaseException(e.getMessage());
		}
		return subjectCode;
	}

}

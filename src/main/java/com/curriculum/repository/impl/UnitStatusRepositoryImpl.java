package com.curriculum.repository.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.UnitStatus;
import com.curriculum.entity.UnitStatusEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.UnitStatusRepository;
import com.curriculum.util.UnitStatusMapper;

@Repository
@Transactional
public class UnitStatusRepositoryImpl implements UnitStatusRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(UnitStatusRepositoryImpl.class);

	@Override
	public Long addUnitStatus(UnitStatus unitStatus) throws DatabaseException, NotAllowedException {
		logger.info("Adding unit status...");
		Session session = null;
		Long statusId = 0l;
		try {
			session = sessionFactory.getCurrentSession();
			UnitStatusEntity unitStatusEntity = getStatusByUnitNo(unitStatus.getUnit().getUnitNo(),
					unitStatus.getTeacher().getId(), unitStatus.getClassDetail().getRoomNo());
			if (unitStatusEntity == null) {
				statusId = (Long) session.save(UnitStatusMapper.mapUnitStatus(unitStatus));
				if (statusId > 0) {
					logger.info("Unit status added successfully!");
				}
			} else {
				throw new NotAllowedException("Already added status for particular unit!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding the unit status!");
			throw new DatabaseException(e.getMessage());
		}
		return statusId;
	}

	@Override
	public UnitStatusEntity getStatusByUnitNo(String unitNo, Long staffId, Long roomNo) throws DatabaseException {
		logger.info("Geeting unit status...");
		Session session = null;
		UnitStatusEntity unitStatusEntity = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query<UnitStatusEntity> query = session.createQuery(
					"FROM UnitStatusEntity t WHERE t.unit.unitNo=:unitNo AND t.teacher.id=:staffId AND t.classDetail.roomNo=:roomNo");
			query.setParameter("unitNo", unitNo);
			query.setParameter("staffId", staffId);
			query.setParameter("roomNo", roomNo);
			unitStatusEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Unit status fetched succeddfully!");
		} catch (HibernateException e) {
			logger.error("Error while getting unit status!");
			throw new DatabaseException(e.getMessage());
		}
		return unitStatusEntity;
	}

	@Override
	public UnitStatusEntity getUnitStatus(Long id) throws DatabaseException, NotFoundException {
		logger.info("Geeting unit status...");
		Session session = null;
		UnitStatusEntity unitStatusEntity = null;
		try {
			checkStatusId(id);
			session = sessionFactory.getCurrentSession();
			Query<UnitStatusEntity> query = session.createQuery("FROM UnitStatusEntity t WHERE t.id=:id");
			query.setParameter("id", id);
			unitStatusEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Unit status fetched succeddfully!");
		} catch (HibernateException e) {
			logger.error("Error while getting unit status!");
			throw new DatabaseException(e.getMessage());
		}
		return unitStatusEntity;
	}

	@Override
	public UnitStatusEntity updateUnitStatus(Long id, UnitStatus unitStatus)
			throws DatabaseException, NotFoundException {
		logger.info("Updating unit status details!");
		Session session = null;
		UnitStatusEntity unitStatusEntity = null;
		try {
			checkStatusId(id);
			session = sessionFactory.getCurrentSession();
			UnitStatusEntity unitStatusDetail = new UnitStatusEntity();
			unitStatusDetail.setBeginDate(unitStatus.getBeginDate());
			unitStatusDetail.setStatus(unitStatus.getStatus());
			unitStatusDetail.setCompletedDate(unitStatus.getCompletedDate());
			unitStatusDetail.setRemarks(unitStatus.getRemarks());
			session.find(UnitStatusEntity.class, id);
			UnitStatusEntity updatedUnitStatusEntity = session.load(UnitStatusEntity.class, id);
			updatedUnitStatusEntity.setBeginDate(unitStatusDetail.getBeginDate());
			updatedUnitStatusEntity.setStatus(unitStatusDetail.getStatus());
			updatedUnitStatusEntity.setCompletedDate(unitStatusDetail.getCompletedDate());
			updatedUnitStatusEntity.setRemarks(unitStatusDetail.getRemarks());
			unitStatusEntity = (UnitStatusEntity) session.merge(updatedUnitStatusEntity);
			logger.info("Unit status details updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the unit status details!");
			throw new DatabaseException(e.getMessage());
		}
		return unitStatusEntity;
	}

	@Override
	public UnitStatusEntity deleteUnitStatus(Long id) throws DatabaseException, NotFoundException {
		logger.info("Deleting the unit status details!");
		Session session = null;
		UnitStatusEntity unitStatusEntity = null;
		try {
			checkStatusId(id);
			session = sessionFactory.getCurrentSession();
			session.find(UnitStatusEntity.class, id);
			UnitStatusEntity unitStatusDetail = session.load(UnitStatusEntity.class, id);
			session.delete(unitStatusDetail);
			UnitStatusEntity deletedUnitStatusEntity = session.get(UnitStatusEntity.class, id);
			if (deletedUnitStatusEntity == null) {
				unitStatusEntity = unitStatusDetail;
				logger.info("Unit status is deleted successfully!");
			} else {
				logger.error("Error while deleting the unit status details!");
			}
		} catch (HibernateException e) {
			logger.error("Error while deleting the unit status details!");
			throw new DatabaseException(e.getMessage());
		}
		return unitStatusEntity;
	}

	@Override
	public void checkStatusId(Long id) throws NotFoundException {
		Session session = sessionFactory.getCurrentSession();
		Query<UnitStatusEntity> query = session.createQuery("FROM UnitStatusEntity WHERE id=:id");
		query.setParameter("id", id);
		UnitStatusEntity unitStatusEntity = query.uniqueResultOptional().orElse(null);
		if (unitStatusEntity == null) {
			throw new NotFoundException("Unit status not found with" + " " + id + "!");
		}
	}

}

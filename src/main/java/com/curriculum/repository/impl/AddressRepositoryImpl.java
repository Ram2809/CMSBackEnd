package com.curriculum.repository.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.Address;
import com.curriculum.entity.AddressEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.repository.AddressRepository;
import com.curriculum.util.AddressMapper;

@Repository
@Transactional
public class AddressRepositoryImpl implements AddressRepository {
	@Autowired
	private SessionFactory sessionFactory;
	private Logger logger = Logger.getLogger(AddressRepositoryImpl.class);

	@Override
	public Long addAddress(Address address) throws DatabaseException {
		logger.info("Adding address details...");
		Session session = null;
		Long addressId = null;
		try {
			session = sessionFactory.getCurrentSession();
			addressId = (Long) session.save(AddressMapper.mapAddress(address));
			if (addressId > 0) {
				logger.info("Address added successfully!");
			}
		} catch (HibernateException e) {
			logger.error("Error while adding address!");
			throw new DatabaseException(e.getMessage());
		}
		return addressId;
	}

	@Override
	public AddressEntity getAddress(Long staffId) throws DatabaseException {
		logger.info("Getting address details...");
		Session session = null;
		AddressEntity addressEntity = null;
		try {
			session = sessionFactory.getCurrentSession();
			Query<AddressEntity> query = session.createQuery("FROM AddressEntity a WHERE a.teacher.id=:staffId");
			query.setParameter("staffId", staffId);
			addressEntity = query.uniqueResultOptional().orElse(null);
			logger.info("Address details are fetched successfully!");
		} catch (HibernateException e) {
			logger.error("Error while fetching address details!");
			throw new DatabaseException(e.getMessage());
		}
		return addressEntity;
	}

	@Override
	public AddressEntity updateAddress(Long id, Address address) throws DatabaseException {
		logger.info("Updating the address details...");
		AddressEntity addressEntity = null;
		Session session = null;
		try {
			session = sessionFactory.getCurrentSession();
			session.find(AddressEntity.class, id);
			AddressEntity updatedAddressEntity = session.load(AddressEntity.class, id);
			updatedAddressEntity.setAddressLine(address.getAddressLine());
			updatedAddressEntity.setCity(address.getCity());
			updatedAddressEntity.setDistrict(address.getDistrict());
			updatedAddressEntity.setState(address.getState());
			updatedAddressEntity.setCountry(address.getCountry());
			updatedAddressEntity.setPinCode(address.getPinCode());
			addressEntity = (AddressEntity) session.merge(updatedAddressEntity);
			logger.info("Address Details are updated successfully!");
		} catch (HibernateException e) {
			logger.error("Error while updating the address!");
			throw new DatabaseException(e.getMessage());
		}
		return addressEntity;
	}

}

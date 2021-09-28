package com.curriculum.repository.impl;

import javax.transaction.Transactional;

import org.apache.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.curriculum.dto.Address;
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
		logger.info("Adding address details!");
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

}

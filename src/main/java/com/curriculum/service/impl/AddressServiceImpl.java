package com.curriculum.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Address;
import com.curriculum.entity.AddressEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.AddressRepository;
import com.curriculum.repository.TeacherRepository;
import com.curriculum.service.AddressService;

@Service
public class AddressServiceImpl implements AddressService {
	@Autowired
	private AddressRepository addressRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	private Logger logger = Logger.getLogger(AddressServiceImpl.class);

	@Override
	public Long addAddress(Address address) throws BusinessServiceException, NotFoundException {
		try {
			teacherRepository.checkTeacher(address.getTeacher().getId());
			return addressRepository.addAddress(address);
		} catch (DataIntegrityViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public AddressEntity getAddress(Long staffId) throws NotFoundException, BusinessServiceException {
		teacherRepository.checkTeacher(staffId);
		try {
			return addressRepository.getAddress(staffId);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public AddressEntity updateAddress(Long id, Address address) throws BusinessServiceException, NotFoundException{
		try {
			return addressRepository.updateAddress(id,address);
		} catch (DataIntegrityViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

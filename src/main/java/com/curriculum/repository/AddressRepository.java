package com.curriculum.repository;

import com.curriculum.dto.Address;
import com.curriculum.entity.AddressEntity;
import com.curriculum.exception.DatabaseException;

public interface AddressRepository {
	Long addAddress(Address address) throws DatabaseException;
	AddressEntity getAddress(Long staffId) throws DatabaseException;
	AddressEntity updateAddress(Long id,Address address) throws DatabaseException;
}

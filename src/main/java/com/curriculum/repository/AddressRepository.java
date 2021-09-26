package com.curriculum.repository;

import com.curriculum.dto.Address;
import com.curriculum.exception.DatabaseException;

public interface AddressRepository {
	Long addAddress(Address address) throws DatabaseException;
}

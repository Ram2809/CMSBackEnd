package com.curriculum.service;

import com.curriculum.dto.Address;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface AddressService {
	Long addAddress(Address address) throws BusinessServiceException, NotFoundException;
}

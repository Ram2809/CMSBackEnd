package com.curriculum.service;

import com.curriculum.dto.Login;
import com.curriculum.entity.LoginEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface LoginService {
	Long addLogin(Login login) throws BusinessServiceException, NotFoundException;
	LoginEntity getLogin(Long teacherId) throws BusinessServiceException, NotFoundException;
	LoginEntity updateLogin(Long teacherId,Login login) throws BusinessServiceException, NotFoundException;
}

package com.curriculum.repository;

import com.curriculum.dto.Login;
import com.curriculum.entity.LoginEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface LoginRepository {
	Long addLogin(Login login) throws DatabaseException, NotFoundException;

	LoginEntity getLogin(Long teacherId) throws DatabaseException;

	LoginEntity updateLogin(Long teacherId, Login login) throws DatabaseException;
}

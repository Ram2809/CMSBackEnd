package com.curriculum.repository;

import com.curriculum.dto.Login;
import com.curriculum.entity.LoginEntity;
import com.curriculum.exception.DatabaseException;

public interface LoginRepository {
	Long addLogin(Login login) throws DatabaseException;
	LoginEntity getLogin(Long teacherId) throws DatabaseException;
	LoginEntity updateLogin(Long teacherId,Login login) throws DatabaseException;
}

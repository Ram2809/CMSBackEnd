package com.curriculum.repository;

import com.curriculum.entity.Login;
import com.curriculum.exception.DatabaseException;

public interface LoginRepository {
	Login addLogin(Login loginDetails) throws DatabaseException;
	Login getLogin(Long teacherId) throws DatabaseException;
	Login updateLogin(Long teacherId,Login loginDetails) throws DatabaseException;
}

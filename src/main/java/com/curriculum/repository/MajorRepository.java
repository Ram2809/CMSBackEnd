package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Major;
import com.curriculum.entity.MajorEntity;
import com.curriculum.exception.DatabaseException;

public interface MajorRepository {
	Long addMajor(Major major) throws DatabaseException;

	List<MajorEntity> getMajors() throws DatabaseException;
}

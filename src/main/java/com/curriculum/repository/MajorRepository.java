package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Major;
import com.curriculum.entity.MajorEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface MajorRepository {
	Long addMajor(Major major) throws DatabaseException;

	List<MajorEntity> getMajors() throws DatabaseException;

	MajorEntity deleteMajor(Long majorId) throws NotFoundException, DatabaseException;
}

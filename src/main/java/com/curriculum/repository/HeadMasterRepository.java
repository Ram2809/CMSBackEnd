package com.curriculum.repository;

import com.curriculum.dto.HeadMaster;
import com.curriculum.entity.HeadMasterEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface HeadMasterRepository {
	Long addHeadMaster(HeadMaster headMaster) throws DatabaseException;

	HeadMasterEntity updateHeadMaster(Long id, HeadMaster headMaster) throws DatabaseException, NotFoundException;

	HeadMasterEntity deleteHeadMaster(Long id) throws DatabaseException, NotFoundException;

	HeadMasterEntity getHeadMaster(String email) throws DatabaseException, NotFoundException;
}

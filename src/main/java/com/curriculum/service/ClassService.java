package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Class;
import com.curriculum.entity.ClassEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface ClassService {
	Long addClass(Class classDetail) throws BusinessServiceException, NotFoundException;

	List<ClassEntity> getAllClass() throws BusinessServiceException;

	ClassEntity updateClass(Long roomNo, Class classDetail) throws BusinessServiceException, NotFoundException;

	ClassEntity deleteClass(Long roomNo) throws BusinessServiceException, NotFoundException;

	ClassEntity getParticularClass(Long roomNo) throws BusinessServiceException, NotFoundException;

	List<ClassEntity> getClassList(String standard) throws BusinessServiceException, NotFoundException;

	Long getClassRoomNo(String standard, String section) throws BusinessServiceException, NotFoundException;

	List<ClassEntity> getClassList(List<Long> roomNoList) throws BusinessServiceException, NotFoundException;
}

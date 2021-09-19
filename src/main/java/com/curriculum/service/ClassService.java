package com.curriculum.service;

import java.util.List;

import com.curriculum.entity.ClassEntity;
import com.curriculum.exception.BusinessServiceException;

public interface ClassService {
	ClassEntity addClass(ClassEntity classDetails) throws BusinessServiceException;

	List<ClassEntity> getAllClass() throws BusinessServiceException;

	ClassEntity updateClass(Long roomNo, ClassEntity classDetails) throws BusinessServiceException;

	ClassEntity deleteClass(Long roomNo) throws BusinessServiceException;

	ClassEntity getParticularClass(Long roomNo) throws BusinessServiceException;

	List<String> getSection(String standard) throws BusinessServiceException;

	Long getClassRoomNo(String standard, String section) throws BusinessServiceException;
}

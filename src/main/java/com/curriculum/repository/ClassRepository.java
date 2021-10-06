package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Class;
import com.curriculum.entity.ClassEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;
import com.curriculum.exception.NotFoundException;

public interface ClassRepository {
	Long addClass(Class classDetail) throws DatabaseException, NotAllowedException;

	List<ClassEntity> getAllClass() throws DatabaseException;

	ClassEntity updateClass(Long roomNo, Class classDetail) throws DatabaseException, NotFoundException;

	ClassEntity deleteClass(Long roomNo) throws DatabaseException, NotFoundException;

	ClassEntity getParticularClass(Long roomNo) throws DatabaseException, NotFoundException;

	List<ClassEntity> getClassList(String standard) throws DatabaseException, NotFoundException;

	Long getClassRoomNo(String standard, String section) throws DatabaseException, NotFoundException;

	void checkClassRoom(Long id) throws NotFoundException;

	List<ClassEntity> getClassList(List<Long> roomNoList) throws DatabaseException, NotFoundException;
}

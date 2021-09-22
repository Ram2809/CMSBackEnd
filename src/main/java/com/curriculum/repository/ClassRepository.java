package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Class;
import com.curriculum.exception.DatabaseException;

public interface ClassRepository {
	Class addClass(Class classDetails) throws DatabaseException;

//	List<ClassEntity> getAllClass() throws DatabaseException;
//
//	ClassEntity updateClass(Long roomNo, ClassEntity classDetails) throws DatabaseException;
//
//	ClassEntity deleteClass(Long roomNo) throws DatabaseException;
//
//	ClassEntity getParticularClass(Long roomNo) throws DatabaseException;
//
//	List<String> getSection(String standard) throws DatabaseException;
//
//	Long getClassRoomNo(String standard, String section) throws DatabaseException;
}

package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.ClassEntity;
import com.curriculum.exception.DatabaseException;

public interface ClassRepository {
	ClassEntity addClass(ClassEntity classDetails) throws DatabaseException;
	List<ClassEntity> getAllClass() throws DatabaseException;
	ResponseEntity<String> updateClassDetails(Long roomNo,ClassEntity classDetails);
	ResponseEntity<String> deleteClassDetails(Long roomNo);
	ResponseEntity<List<ClassEntity>> getParticularClassDetails(Long roomNo) throws ClassNotFoundException;
	ResponseEntity<List<String>> getSection(String standard);
	ResponseEntity<Long> getClassDetails(String standard,String section);
}

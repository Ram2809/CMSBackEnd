package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.ClassEntity;
import com.curriculum.exception.BusinessServiceException;

public interface ClassService {
	ClassEntity addClass(ClassEntity classDetails) throws BusinessServiceException;

	List<ClassEntity> getAllClass() throws BusinessServiceException;

	ResponseEntity<String> updateClassDetails(Long roomNo, ClassEntity classDetails);

	ResponseEntity<String> deleteClassDetails(Long roomNo);

	ResponseEntity<List<ClassEntity>> getParticularClassDetails(Long roomNo) throws ClassNotFoundException;

	ResponseEntity<List<String>> getSection(String standard);

	ResponseEntity<Long> getClassDetails(String standard, String section);
}

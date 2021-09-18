package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.ClassEntity;

public interface ClassService {
	ResponseEntity<String> addClassDetails(ClassEntity classDetails);

	ResponseEntity<List<ClassEntity>> getAllClassDetails();

	ResponseEntity<String> updateClassDetails(Long roomNo, ClassEntity classDetails);

	ResponseEntity<String> deleteClassDetails(Long roomNo);

	ResponseEntity<List<ClassEntity>> getParticularClassDetails(Long roomNo) throws ClassNotFoundException;

	ResponseEntity<List<String>> getSection(String standard);

	ResponseEntity<Long> getClassDetails(String standard, String section);
}

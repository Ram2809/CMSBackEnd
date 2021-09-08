package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.ClassEntity;
import com.curriculum.repository.ClassRepository;
import com.curriculum.service.ClassService;

@Service
public class ClassServiceImpl implements ClassService{
	@Autowired
	private ClassRepository classRepositoryImpl;
	@Override
	public ResponseEntity<String> addClassDetails(ClassEntity classDetails) {
		// TODO Auto-generated method stub
		return classRepositoryImpl.addClassDetails(classDetails);
	}
	@Override
	public ResponseEntity<List<ClassEntity>> getAllClassDetails() {
		return classRepositoryImpl.getAllClassDetails();
	}
	@Override
	public ResponseEntity<String> updateClassDetails(Long roomNo, ClassEntity classDetails) {
		// TODO Auto-generated method stub
		return classRepositoryImpl.updateClassDetails(roomNo,classDetails);
	}
	@Override
	public ResponseEntity<String> deleteClassDetails(Long roomNo) {
		// TODO Auto-generated method stub
		return classRepositoryImpl.deleteClassDetails(roomNo);
	}
	@Override
	public ResponseEntity<List<ClassEntity>> getParticularClassDetails(Long roomNo) throws ClassNotFoundException {
		// TODO Auto-generated method stub
		return classRepositoryImpl.getParticularClassDetails(roomNo);
	}
	@Override
	public ResponseEntity<List<String>> getSection(String standard) {
		// TODO Auto-generated method stub
		return classRepositoryImpl.getSection(standard);
	}
	@Override
	public ResponseEntity<Long> getClassDetails(String standard, String section) {
		// TODO Auto-generated method stub
		return classRepositoryImpl.getClassDetails(standard,section);
	}

}

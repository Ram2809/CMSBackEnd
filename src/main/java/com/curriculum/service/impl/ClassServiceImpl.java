package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Class;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.repository.ClassRepository;
import com.curriculum.service.ClassService;

@Service
public class ClassServiceImpl implements ClassService{
	@Autowired
	private ClassRepository classRepository;
	@Override
	public Class addClass(Class classDetails) throws BusinessServiceException {
		try {
			return classRepository.addClass(classDetails);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
//	@Override
//	public List<ClassEntity> getAllClass() throws BusinessServiceException {
//		try {
//			return classRepository.getAllClass();
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public ClassEntity updateClass(Long roomNo, ClassEntity classDetails) throws BusinessServiceException {
//		try {
//			return classRepository.updateClass(roomNo,classDetails);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public ClassEntity deleteClass(Long roomNo) throws BusinessServiceException {
//		try {
//			return classRepository.deleteClass(roomNo);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public ClassEntity getParticularClass(Long roomNo) throws BusinessServiceException  {
//		try {
//			return classRepository.getParticularClass(roomNo);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public List<String> getSection(String standard) throws BusinessServiceException {
//		try {
//			return classRepository.getSection(standard);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public Long getClassRoomNo(String standard, String section) throws BusinessServiceException {
//		try {
//			return classRepository.getClassRoomNo(standard,section);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}

}

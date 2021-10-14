package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Major;
import com.curriculum.entity.MajorEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.repository.MajorRepository;
import com.curriculum.service.MajorService;

@Service
public class MajorServiceImpl implements MajorService {
	@Autowired
	private MajorRepository majorRepository;

	@Override
	public Long addMajor(Major major) throws BusinessServiceException {
		try {
			return majorRepository.addMajor(major);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<MajorEntity> getMajors() throws BusinessServiceException {
		try {
			return majorRepository.getMajors();
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

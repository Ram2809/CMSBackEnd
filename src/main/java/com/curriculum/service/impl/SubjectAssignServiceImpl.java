package com.curriculum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.ClassRepository;
import com.curriculum.repository.SubjectAssignRepository;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.service.SubjectAssignService;

@Service
public class SubjectAssignServiceImpl implements SubjectAssignService{
	@Autowired
	private SubjectAssignRepository subjectAssignRepository;
	@Autowired
	private SubjectRepository subjectRepository;
	@Autowired 
	private ClassRepository classRepository;
	@Override
	public Long addSubjectAssign(SubjectAssign subjectAssign) throws BusinessServiceException, NotFoundException {
		try {
			subjectRepository.checkSubject(subjectAssign.getSubject().getCode());
			classRepository.checkClassRoom(subjectAssign.getClassDetail().getRoomNo());
			return subjectAssignRepository.addSubjectAssign(subjectAssign);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

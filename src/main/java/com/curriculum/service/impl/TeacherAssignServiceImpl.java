package com.curriculum.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.curriculum.dto.TeacherAssign;
import com.curriculum.entity.TeacherAssignEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.SubjectAssignRepository;
import com.curriculum.repository.TeacherAssignRepository;
import com.curriculum.repository.TeacherRepository;
import com.curriculum.service.TeacherAssignService;

@Service
public class TeacherAssignServiceImpl implements TeacherAssignService {
	@Autowired
	private TeacherAssignRepository teacherAssignRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private SubjectAssignRepository subjectAssignRepository;
	private Logger logger = Logger.getLogger(TeacherAssignServiceImpl.class);

	@Override
	public Long assignTeacherSubject(TeacherAssign teacherAssign) throws BusinessServiceException, NotFoundException {
		try {
			teacherRepository.checkTeacher(teacherAssign.getTeacher().getId());
			subjectAssignRepository.checkAssignId(teacherAssign.getSubjectAssign().getId());
			return teacherAssignRepository.assignTeacherSubject(teacherAssign);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		} catch (DataIntegrityViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		}
	}

	@Override
	public TeacherAssignEntity deleteTeacherSubjectAssign(Long id) throws BusinessServiceException, NotFoundException {
		try {
			return teacherAssignRepository.deleteTeacherSubjectAssign(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<Long> getSubjectAssignIds(Long staffId) throws BusinessServiceException, NotFoundException {
		teacherRepository.checkTeacher(staffId);
		try {
			return teacherAssignRepository.getSubjectAssignIds(staffId);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public Long getTeacherId(Long id) throws BusinessServiceException, NotFoundException {
		try {
			subjectAssignRepository.checkAssignId(id);
			return teacherAssignRepository.getTeacherId(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public Long updateTeacherAssign(Long assignId, Long staffId) throws BusinessServiceException, NotFoundException {
		subjectAssignRepository.checkAssignId(assignId);
		teacherRepository.checkTeacher(staffId);
		try {
			return teacherAssignRepository.updateTeacherAssign(assignId, staffId);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<Long> getTeacherIdList(List<Long> assignIdList) throws NotFoundException, BusinessServiceException {
		for (Long assignId : assignIdList) {
			subjectAssignRepository.checkAssignId(assignId);
		}
		try {
			return teacherAssignRepository.getTeacherIdList(assignIdList);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

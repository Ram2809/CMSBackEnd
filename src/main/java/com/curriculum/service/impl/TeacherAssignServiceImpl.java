package com.curriculum.service.impl;

import javax.validation.ConstraintViolationException;

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
			//subjectAssignRepository.checkSubject(teacherAssign.getSubjectAssign().getId());
			return teacherAssignRepository.assignTeacherSubject(teacherAssign);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		} catch (DataIntegrityViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		}
	}

	@Override
	public TeacherAssignEntity updateTeacherSubjectAssign(Long id, TeacherAssign teacherAssign)
			throws BusinessServiceException, NotFoundException {
		try {
			teacherRepository.checkTeacher(teacherAssign.getTeacher().getId());
			//subjectRepository.checkSubject(teacherAssign.getSubject().getCode());
			return teacherAssignRepository.updateTeacherSubjectAssign(id, teacherAssign);
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
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

}

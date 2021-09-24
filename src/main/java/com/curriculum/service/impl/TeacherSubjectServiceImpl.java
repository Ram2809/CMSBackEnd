package com.curriculum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.dto.TeacherSubject;
import com.curriculum.entity.TeacherSubjectEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.TeacherNotFoundException;
import com.curriculum.repository.TeacherSubjectRepository;
import com.curriculum.repository.impl.SubjectRepositoryImpl;
import com.curriculum.repository.impl.TeacherRepositoryImpl;
import com.curriculum.service.TeacherSubjectService;
@Service
public class TeacherSubjectServiceImpl implements TeacherSubjectService{
	@Autowired
	private TeacherSubjectRepository teacherSubjectRepository;
	@Autowired
	private TeacherRepositoryImpl teacherRepository;
	@Autowired
	private SubjectRepositoryImpl subjectRepository;
	@Override
	public Long assignTeacherSubject(TeacherSubject teacherSubject) throws BusinessServiceException, NotFoundException {
		try {
			teacherRepository.checkTeacher(teacherSubject.getTeacher().getId());
			subjectRepository.checkSubject(teacherSubject.getSubject().getCode());
			return teacherSubjectRepository.assignTeacherSubject(teacherSubject);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public TeacherSubjectEntity updateTeacherSubjectAssign(Long id,TeacherSubject teacherSubject) throws BusinessServiceException, NotFoundException {
		try {
			teacherRepository.checkTeacher(teacherSubject.getTeacher().getId());
			subjectRepository.checkSubject(teacherSubject.getSubject().getCode());
			return teacherSubjectRepository.updateTeacherSubjectAssign(id,teacherSubject);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public TeacherSubjectEntity deleteTeacherSubjectAssign(Long id) throws BusinessServiceException, NotFoundException{
		try {
			return teacherSubjectRepository.deleteTeacherSubjectAssign(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

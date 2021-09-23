//package com.curriculum.service.impl;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.curriculum.entity.TeacherSubject;
//import com.curriculum.exception.BusinessServiceException;
//import com.curriculum.exception.DatabaseException;
//import com.curriculum.exception.SubjectNotFoundException;
//import com.curriculum.exception.TeacherNotFoundException;
//import com.curriculum.repository.TeacherSubjectRepository;
//import com.curriculum.service.TeacherSubjectService;
//@Service
//public class TeacherSubjectServiceImpl implements TeacherSubjectService{
//	@Autowired
//	private TeacherSubjectRepository teacherSubjectRepository;
//	@Override
//	public TeacherSubject assignTeacherSubject(TeacherSubject teacherSubjectDetails) throws BusinessServiceException {
//		try {
//			return teacherSubjectRepository.assignTeacherSubject(teacherSubjectDetails);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public TeacherSubject updateTeacherSubjectAssign(Long id,TeacherSubject teacherSubjectDetails) throws BusinessServiceException {
//		try {
//			return teacherSubjectRepository.updateTeacherSubjectAssign(id,teacherSubjectDetails);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public TeacherSubject deleteTeacherSubjectAssign(Long id) throws BusinessServiceException{
//		try {
//			return teacherSubjectRepository.deleteTeacherSubjectAssign(id);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//
//}

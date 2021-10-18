package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.StudentAssignDTO;
import com.curriculum.entity.StudentAssign;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.ClassRepository;
import com.curriculum.repository.StudentAssignRepository;
import com.curriculum.repository.StudentRepository;
import com.curriculum.service.StudentAssignService;

@Service
public class StudentAssignServiceImpl implements StudentAssignService {
	@Autowired
	private StudentAssignRepository studentAssignRepository;
	@Autowired
	private StudentRepository studentRepository;
	@Autowired
	private ClassRepository classRepository;

	@Override
	public Long addStudentAssign(StudentAssignDTO studentAssignDTO) throws BusinessServiceException, NotFoundException {
		studentRepository.checkStudent(studentAssignDTO.getStudent().getRollNo());
		classRepository.checkClassRoom(studentAssignDTO.getClassDetail().getRoomNo());
		try {
			return studentAssignRepository.addStudentAssign(studentAssignDTO);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<StudentAssign> getStudentClassDetails(Long roomNo, String academicYear) throws BusinessServiceException, NotFoundException {
		classRepository.checkClassRoom(roomNo);
		try {
			return studentAssignRepository.getStudentClassDetails(roomNo,academicYear);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public StudentAssign updateStudentAssign(Long assignId, StudentAssignDTO studentAssignDTO) throws BusinessServiceException,NotFoundException {
		studentRepository.checkStudent(studentAssignDTO.getStudent().getRollNo());
		classRepository.checkClassRoom(studentAssignDTO.getClassDetail().getRoomNo());
		try {
			return studentAssignRepository.updateStudentAssign(assignId,studentAssignDTO);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

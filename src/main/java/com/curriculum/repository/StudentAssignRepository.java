package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.StudentAssignDTO;
import com.curriculum.entity.StudentAssign;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;

public interface StudentAssignRepository {
	Long addStudentAssign(StudentAssignDTO studentAssignDTO) throws DatabaseException, NotAllowedException;

	List<StudentAssign> getStudentClassDetails(Long roomNo, String academicYear) throws DatabaseException;

	StudentAssign updateStudentAssign(Long assignId, StudentAssignDTO studentAssignDTO) throws DatabaseException;
}

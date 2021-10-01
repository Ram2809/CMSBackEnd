package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.AssignIdNotFoundException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface SubjectAssignRepository {
	Long addSubjectAssign(SubjectAssign subjectAssign) throws DatabaseException;

	List<SubjectAssignEntity> getSubjects(Long roomNo) throws DatabaseException;

	Long getAssignId(Long roomNo, String subjectCode) throws DatabaseException;

	String getSubjectCode(Long id,Long roomNo) throws DatabaseException, NotFoundException;

	void checkAssignId(Long id) throws AssignIdNotFoundException;

	Long getRoomNo(Long id) throws DatabaseException, NotFoundException;
}
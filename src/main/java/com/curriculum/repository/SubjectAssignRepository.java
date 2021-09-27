package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.exception.DatabaseException;

public interface SubjectAssignRepository {
	Long addSubjectAssign(SubjectAssign subjectAssign) throws DatabaseException;
	List<SubjectAssignEntity> getSubjects(Long roomNo) throws DatabaseException;
}

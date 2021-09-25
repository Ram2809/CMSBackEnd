package com.curriculum.repository;

import com.curriculum.dto.SubjectAssign;
import com.curriculum.exception.DatabaseException;

public interface SubjectAssignRepository {
	Long addSubjectAssign(SubjectAssign subjectAssign) throws DatabaseException;
}

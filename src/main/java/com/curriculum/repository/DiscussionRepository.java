package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Discussion;
import com.curriculum.entity.DiscussionEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface DiscussionRepository {
	Long addDiscussion(Discussion discussion) throws DatabaseException;

	List<DiscussionEntity> getDiscussionByUnitNo(String unitNo) throws DatabaseException;

	DiscussionEntity updateDiscussion(Long questionNo, Discussion discussion)
			throws DatabaseException, NotFoundException;

	DiscussionEntity deleteDiscussion(Long questionNo) throws DatabaseException, NotFoundException;

	DiscussionEntity getParticularDiscussion(Long questionNo) throws DatabaseException, NotFoundException;
}

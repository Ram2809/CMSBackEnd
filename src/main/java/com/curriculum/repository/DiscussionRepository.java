package com.curriculum.repository;

import java.util.List;

import com.curriculum.entity.Discussion;
import com.curriculum.exception.DatabaseException;

public interface DiscussionRepository {
	Discussion addDiscussion(Discussion discussionDetails) throws DatabaseException;

	List<Discussion> getDiscussionByUnitNo(String unitNo) throws DatabaseException;

	Discussion updateDiscussion(Long questionNo, Discussion discussionDetails) throws DatabaseException;

	Discussion deleteDiscussion(Long questionNo) throws DatabaseException;

	Discussion getParticularDiscussion(Long questionNo) throws DatabaseException;
}

package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Discussion;
import com.curriculum.entity.DiscussionEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface DiscussionService {
	Long addDiscussion(Discussion discussion) throws BusinessServiceException, NotFoundException;

	List<DiscussionEntity> getDiscussionByTopicNo(Long topicNo, Long roomNo, Long staffId)
			throws BusinessServiceException, NotFoundException;

	List<DiscussionEntity> getDiscussionByRoomNo(Long topicNo, Long roomNo)
			throws BusinessServiceException, NotFoundException;

	DiscussionEntity updateDiscussion(Long questionNo, Discussion discussion)
			throws BusinessServiceException, NotFoundException;

	DiscussionEntity deleteDiscussion(Long questionNo) throws BusinessServiceException, NotFoundException;

	DiscussionEntity getParticularDiscussion(Long questionNo) throws BusinessServiceException, NotFoundException;
}

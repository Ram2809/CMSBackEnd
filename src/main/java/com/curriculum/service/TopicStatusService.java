package com.curriculum.service;

import com.curriculum.dto.TopicStatus;
import com.curriculum.entity.TopicStatusEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TopicStatusService {
	Long addTopicStatus(TopicStatus topicStatus) throws NotFoundException, BusinessServiceException;

	TopicStatusEntity getStatusByUnitNo(String unitNo, Long staffId, Long roomNo)
			throws NotFoundException, BusinessServiceException;

	TopicStatusEntity getTopicStatus(Long id) throws BusinessServiceException, NotFoundException;

	TopicStatusEntity updateTopicStatus(Long id, TopicStatus topicStatus)
			throws BusinessServiceException, NotFoundException;

	TopicStatusEntity deleteTopicStatus(Long id) throws BusinessServiceException, NotFoundException;
}

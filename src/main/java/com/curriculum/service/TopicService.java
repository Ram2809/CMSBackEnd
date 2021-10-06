package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TopicService {
	Long addTopic(Topic topic) throws BusinessServiceException, NotFoundException;

	List<TopicEntity> getTopics(String unitNoF) throws BusinessServiceException, NotFoundException;
}

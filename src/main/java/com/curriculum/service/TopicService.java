package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TopicService {
	Long addTopic(Topic topic) throws BusinessServiceException, NotFoundException;

	List<TopicEntity> getTopics(String unitNo) throws BusinessServiceException, NotFoundException;

	List<List<TopicEntity>> getTopicList(List<String> unitNoList) throws NotFoundException, BusinessServiceException;

	TopicEntity getTopic(Long topicNo) throws NotFoundException, BusinessServiceException;

	TopicEntity updateTopic(Long topicNo, Topic topic) throws NotFoundException, BusinessServiceException;

	TopicEntity deleteTopic(Long topicNo) throws NotFoundException, BusinessServiceException;
}

package com.curriculum.service;

import java.util.List;

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TopicService {
	String addTopic(Topic topic) throws BusinessServiceException, NotFoundException;

	List<TopicEntity> getTopicBySubjectCode(String subjectCode) throws BusinessServiceException, NotFoundException;

	TopicEntity getTopicByUnitNo(String unitNo) throws BusinessServiceException, NotFoundException;

	TopicEntity updateTopic(String unitNo, Topic topic) throws BusinessServiceException, NotFoundException;

	String getSubjectCode(String unitNo) throws BusinessServiceException, NotFoundException;

	TopicEntity deleteTopic(String unitNo) throws BusinessServiceException, NotFoundException;
}

package com.curriculum.service;

import java.util.List;

import com.curriculum.entity.Topic;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.NotFoundException;

public interface TopicService {
	Topic addTopic(Topic topicDetails) throws BusinessServiceException, NotFoundException;

	List<Topic> getTopicBySubjectCode(String subjectCode) throws BusinessServiceException;

	Topic getTopicByUnitNo(String unitNo) throws BusinessServiceException;

	Topic updateTopic(String unitNo, Topic topicDetails) throws BusinessServiceException;

	String getSubjectCode(String unitNo) throws BusinessServiceException;

	Topic deleteTopic(String unitNo) throws BusinessServiceException;
}

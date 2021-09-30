package com.curriculum.repository;

import com.curriculum.dto.TopicStatus;
import com.curriculum.entity.TopicStatusEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;
import com.curriculum.exception.NotFoundException;

public interface TopicStatusRepository {
	Long addTopicStatus(TopicStatus topicStatus) throws DatabaseException, NotAllowedException;

	TopicStatusEntity getStatusByUnitNo(String unitNo, Long staffId, Long roomNo) throws DatabaseException;

	TopicStatusEntity getTopicStatus(Long id) throws DatabaseException, NotFoundException;

	TopicStatusEntity updateTopicStatus(Long id, TopicStatus topicStatus) throws DatabaseException, NotFoundException;

	TopicStatusEntity deleteTopicStatus(Long id) throws DatabaseException, NotFoundException;

	void checkStatusId(Long id) throws NotFoundException;
}

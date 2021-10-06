package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.DatabaseException;

public interface TopicRepository {
	Long addTopic(Topic topic) throws DatabaseException;

	List<TopicEntity> getTopics(String unitNo) throws DatabaseException;
}

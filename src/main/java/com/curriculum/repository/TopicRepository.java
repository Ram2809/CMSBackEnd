package com.curriculum.repository;

import java.util.List;

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;

public interface TopicRepository {
	Long addTopic(Topic topic) throws DatabaseException;

	List<TopicEntity> getTopics(String unitNo) throws DatabaseException;

	List<List<TopicEntity>> getTopicList(List<String> unitNoList) throws DatabaseException;

	TopicEntity getTopic(Long topicNo) throws DatabaseException, NotFoundException;

	void checkTopicNo(Long topicNo) throws NotFoundException;

	TopicEntity updateTopic(Long topicNo, Topic topic) throws DatabaseException, NotFoundException;

	TopicEntity deleteTopic(Long topicNo) throws DatabaseException, NotFoundException;
}

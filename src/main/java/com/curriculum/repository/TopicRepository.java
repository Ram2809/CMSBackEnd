package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Topic;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.UnitNotFoundException;

public interface TopicRepository {
	Topic addTopic(Topic topicDetails) throws DatabaseException, NotFoundException;

	List<Topic> getTopicBySubjectCode(String subjectCode) throws DatabaseException;

	Topic getTopicByUnitNo(String unitNo) throws DatabaseException;

	Topic updateTopic(String unitNo, Topic topicDetails) throws DatabaseException;

	String getSubjectCode(String unitNo) throws DatabaseException;

	Topic deleteTopic(String unitNo) throws DatabaseException;
}

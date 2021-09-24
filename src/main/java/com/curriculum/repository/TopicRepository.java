package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.UnitNotFoundException;

public interface TopicRepository {
	String addTopic(Topic topic) throws DatabaseException, NotFoundException;

	List<TopicEntity> getTopicBySubjectCode(String subjectCode) throws DatabaseException;

	TopicEntity getTopicByUnitNo(String unitNo) throws DatabaseException, NotFoundException;

	TopicEntity updateTopic(String unitNo, Topic topic) throws DatabaseException, NotFoundException;

	String getSubjectCode(String unitNo) throws DatabaseException,NotFoundException;

	TopicEntity deleteTopic(String unitNo) throws DatabaseException,NotFoundException;
}

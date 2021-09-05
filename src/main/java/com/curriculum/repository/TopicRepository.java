package com.curriculum.repository;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Topic;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.UnitNotFoundException;

public interface TopicRepository {
	ResponseEntity<String> addTopicDetails(String subjectCode,Topic topicDetails) throws SubjectNotFoundException;
	ResponseEntity<List<String>> getTopicDetailsBySubjectCode(String subjectCode);
	ResponseEntity<Topic> getTopicDetailsByUnitNo(String unitNo);
	ResponseEntity<String> updateTopicDetails(String subjectCode,String unitNo,Topic topicDetails) throws SubjectNotFoundException;
	ResponseEntity<String> deleteTopicDetails(String unitNo) throws UnitNotFoundException;
}

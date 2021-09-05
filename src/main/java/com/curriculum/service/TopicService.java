package com.curriculum.service;

import java.util.List;

import org.springframework.http.ResponseEntity;

import com.curriculum.entity.Topic;
import com.curriculum.exception.SubjectNotFoundException;

public interface TopicService {
	ResponseEntity<String> addTopicDetails(String subjectCode,Topic topicDetails) throws SubjectNotFoundException;
	ResponseEntity<List<String>> getTopicDetailsBySubjectCode(String subjectCode);
	ResponseEntity<Topic> getTopicDetailsByUnitNo(String unitNo);
}

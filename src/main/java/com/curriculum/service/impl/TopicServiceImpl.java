package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.curriculum.entity.Topic;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.exception.UnitNotFoundException;
import com.curriculum.repository.TopicRepository;
import com.curriculum.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService{
	@Autowired
	private TopicRepository topicRepositoryImpl;
	@Override
	public ResponseEntity<String> addTopicDetails(String subjectCode, Topic topicDetails) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		return topicRepositoryImpl.addTopicDetails(subjectCode,topicDetails);
	}
	@Override
	public ResponseEntity<List<Topic>> getTopicDetailsBySubjectCode(String subjectCode) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		return topicRepositoryImpl.getTopicDetailsBySubjectCode(subjectCode);
	}
	@Override
	public ResponseEntity<Topic> getTopicDetailsByUnitNo(String unitNo) {
		// TODO Auto-generated method stub
		return topicRepositoryImpl.getTopicDetailsByUnitNo(unitNo);
	}
	@Override
	public ResponseEntity<String> updateTopicDetails(String subjectCode, String unitNo, Topic topicDetails) throws SubjectNotFoundException {
		// TODO Auto-generated method stub
		return topicRepositoryImpl.updateTopicDetails(subjectCode,unitNo,topicDetails);
	}
	@Override
	public ResponseEntity<String> deleteTopicDetails(String unitNo) throws UnitNotFoundException {
		// TODO Auto-generated method stub
		return topicRepositoryImpl.deleteTopicDetails(unitNo);
	}

}

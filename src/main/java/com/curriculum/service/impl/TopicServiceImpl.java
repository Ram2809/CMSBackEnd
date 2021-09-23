package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Topic;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.repository.TopicRepository;
import com.curriculum.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService{
	@Autowired
	private TopicRepository topicRepositoryImpl;
	@Autowired
	private SubjectRepository subjectRepository;
	@Override
	public String addTopic(Topic topic) throws BusinessServiceException, NotFoundException {
		String unitNo=null;
		try {
			subjectRepository.checkSubject(topic.getSubject().getCode());
			unitNo=topicRepositoryImpl.addTopic(topic);
		}
		catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
		return unitNo;
	}
//	@Override
//	public List<Topic> getTopicBySubjectCode(String subjectCode) throws BusinessServiceException {
//		try {
//			return topicRepositoryImpl.getTopicBySubjectCode(subjectCode);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public Topic getTopicByUnitNo(String unitNo) throws BusinessServiceException {
//		try {
//			return topicRepositoryImpl.getTopicByUnitNo(unitNo);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public Topic updateTopic(String unitNo, Topic topicDetails) throws BusinessServiceException {
//		try {
//			return topicRepositoryImpl.updateTopic(unitNo,topicDetails);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//
//	@Override
//	public Topic deleteTopic(String unitNo) throws BusinessServiceException { 
//		try {
//			return topicRepositoryImpl.deleteTopic(unitNo);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}
//	@Override
//	public String getSubjectCode(String unitNo) throws BusinessServiceException {
//		try {
//			return topicRepositoryImpl.getSubjectCode(unitNo);
//		} catch (DatabaseException e) {
//			throw new BusinessServiceException(e.getMessage());
//		}
//	}

}

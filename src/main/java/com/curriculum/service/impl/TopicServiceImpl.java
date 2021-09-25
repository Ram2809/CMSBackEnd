package com.curriculum.service.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import javax.validation.ConstraintViolationException;
import org.springframework.stereotype.Service;

import com.curriculum.controller.TopicController;
import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.exception.SubjectNotFoundException;
import com.curriculum.repository.SubjectRepository;
import com.curriculum.repository.TopicRepository;
import com.curriculum.repository.impl.SubjectRepositoryImpl;
import com.curriculum.service.TopicService;

@Service
public class TopicServiceImpl implements TopicService {
	@Autowired
	private TopicRepository topicRepositoryImpl;
	@Autowired
	private SubjectRepository subjectRepository;
	private Logger logger = Logger.getLogger(TopicServiceImpl.class);

	@Override
	public String addTopic(Topic topic) throws BusinessServiceException, NotFoundException {
		try {
			subjectRepository.checkSubject(topic.getSubject().getCode());
			return topicRepositoryImpl.addTopic(topic);
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<TopicEntity> getTopicBySubjectCode(String subjectCode)
			throws BusinessServiceException, NotFoundException {
		try {
			subjectRepository.checkSubject(subjectCode);
			return topicRepositoryImpl.getTopicBySubjectCode(subjectCode);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public TopicEntity getTopicByUnitNo(String unitNo) throws BusinessServiceException, NotFoundException {
		try {
			return topicRepositoryImpl.getTopicByUnitNo(unitNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public TopicEntity updateTopic(String unitNo, Topic topic) throws BusinessServiceException, NotFoundException {
		try {
			subjectRepository.checkSubject(topic.getSubject().getCode());
			return topicRepositoryImpl.updateTopic(unitNo, topic);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public TopicEntity deleteTopic(String unitNo) throws BusinessServiceException, NotFoundException {
		try {
			return topicRepositoryImpl.deleteTopic(unitNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public String getSubjectCode(String unitNo) throws BusinessServiceException, NotFoundException {
		try {
			return topicRepositoryImpl.getSubjectCode(unitNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

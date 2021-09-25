package com.curriculum.service.impl;

import java.util.List;

import javax.validation.ConstraintViolationException;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Discussion;
import com.curriculum.entity.DiscussionEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.ConstraintValidationException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.DiscussionRepository;
import com.curriculum.repository.TopicRepository;
import com.curriculum.service.DiscussionService;

@Service
public class DiscussionServiceImpl implements DiscussionService {
	@Autowired
	private DiscussionRepository discussionRepository;
	@Autowired
	private TopicRepository topicRepository;
	private Logger logger = Logger.getLogger(DiscussionServiceImpl.class);

	@Override
	public Long addDiscussion(Discussion discussion) throws BusinessServiceException, NotFoundException {
		try {
			topicRepository.checkTopic(discussion.getTopic().getUnitNo());
			return discussionRepository.addDiscussion(discussion);
		} catch (DataIntegrityViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public List<DiscussionEntity> getDiscussionByUnitNo(String unitNo)
			throws BusinessServiceException, NotFoundException {
		try {
			topicRepository.checkTopic(unitNo);
			return discussionRepository.getDiscussionByUnitNo(unitNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public DiscussionEntity updateDiscussion(Long questionNo, Discussion discussion)
			throws BusinessServiceException, NotFoundException {
		try {
			topicRepository.checkTopic(discussion.getTopic().getUnitNo());
			return discussionRepository.updateDiscussion(questionNo, discussion);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		} catch (DataIntegrityViolationException | ConstraintViolationException e) {
			logger.error("Constraint Violation fails!");
			throw new ConstraintValidationException("Constraint Violation fails!");
		}
	}

	@Override
	public DiscussionEntity deleteDiscussion(Long questionNo) throws BusinessServiceException, NotFoundException {
		try {
			return discussionRepository.deleteDiscussion(questionNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public DiscussionEntity getParticularDiscussion(Long questionNo)
			throws BusinessServiceException, NotFoundException {
		try {
			return discussionRepository.getParticularDiscussion(questionNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

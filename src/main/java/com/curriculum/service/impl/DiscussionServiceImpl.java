package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Discussion;
import com.curriculum.entity.DiscussionEntity;
import com.curriculum.exception.BusinessServiceException;
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

	@Override
	public Long addDiscussion(Discussion discussion) throws BusinessServiceException, NotFoundException {
		try {
			topicRepository.checkTopic(discussion.getTopic().getUnitNo());
			return discussionRepository.addDiscussion(discussion);
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

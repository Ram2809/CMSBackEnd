package com.curriculum.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.TopicRepository;
import com.curriculum.repository.UnitRepository;
import com.curriculum.service.TopicService;
@Service
public class TopicServiceImpl implements TopicService{
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private UnitRepository unitRepository;
	@Override
	public Long addTopic(Topic topic) throws BusinessServiceException, NotFoundException {
		unitRepository.checkUnit(topic.getUnit().getUnitNo());
		try {
			return topicRepository.addTopic(topic);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}
	@Override
	public List<TopicEntity> getTopics(String unitNo) throws BusinessServiceException, NotFoundException {
		unitRepository.checkUnit(unitNo);
		try {
			return topicRepository.getTopics(unitNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

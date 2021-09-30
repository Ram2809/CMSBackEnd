package com.curriculum.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.curriculum.dto.TopicStatus;
import com.curriculum.entity.TopicStatusEntity;
import com.curriculum.exception.BusinessServiceException;
import com.curriculum.exception.DatabaseException;
import com.curriculum.exception.NotAllowedException;
import com.curriculum.exception.NotFoundException;
import com.curriculum.repository.ClassRepository;
import com.curriculum.repository.TeacherRepository;
import com.curriculum.repository.TopicRepository;
import com.curriculum.repository.TopicStatusRepository;
import com.curriculum.service.TopicStatusService;

@Service
public class TopicStatusServiceImpl implements TopicStatusService {
	@Autowired
	private TopicStatusRepository topicStatusRepository;
	@Autowired
	private TopicRepository topicRepository;
	@Autowired
	private TeacherRepository teacherRepository;
	@Autowired
	private ClassRepository classRepository;

	@Override
	public Long addTopicStatus(TopicStatus topicStatus) throws NotFoundException, BusinessServiceException, NotAllowedException {
		topicRepository.checkTopic(topicStatus.getTopic().getUnitNo());
		teacherRepository.checkTeacher(topicStatus.getTeacher().getId());
		classRepository.checkClassRoom(topicStatus.getClassDetail().getRoomNo());
		try {
			return topicStatusRepository.addTopicStatus(topicStatus);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public TopicStatusEntity getStatusByUnitNo(String unitNo, Long staffId, Long roomNo) throws NotFoundException, BusinessServiceException {
		topicRepository.checkTopic(unitNo);
		teacherRepository.checkTeacher(staffId);
		classRepository.checkClassRoom(roomNo);
		try {
			return topicStatusRepository.getStatusByUnitNo(unitNo,staffId,roomNo);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public TopicStatusEntity getTopicStatus(Long id) throws BusinessServiceException, NotFoundException {
		try {
			return topicStatusRepository.getTopicStatus(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public TopicStatusEntity updateTopicStatus(Long id, TopicStatus topicStatus) throws BusinessServiceException, NotFoundException {
		try {
			return topicStatusRepository.updateTopicStatus(id,topicStatus);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

	@Override
	public TopicStatusEntity deleteTopicStatus(Long id) throws BusinessServiceException, NotFoundException {
		try {
			return topicStatusRepository.deleteTopicStatus(id);
		} catch (DatabaseException e) {
			throw new BusinessServiceException(e.getMessage());
		}
	}

}

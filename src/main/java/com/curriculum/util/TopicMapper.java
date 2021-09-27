package com.curriculum.util;

import com.curriculum.dto.Topic;
import com.curriculum.entity.SubjectEntity;
import com.curriculum.entity.TopicEntity;

public class TopicMapper {
	public static TopicEntity topicMapper(Topic topic) {
		TopicEntity topicEntity = new TopicEntity();
		topicEntity.setUnitNo(topic.getUnitNo());
		topicEntity.setUnitName(topic.getUnitName());
		topicEntity.setDescription(topic.getDescription());
		topicEntity.setMonth(topic.getMonth());
		SubjectEntity subjectEntity = new SubjectEntity();
		subjectEntity.setCode(topic.getSubject().getCode());
		topicEntity.setSubject(subjectEntity);
		return topicEntity;
	}
}

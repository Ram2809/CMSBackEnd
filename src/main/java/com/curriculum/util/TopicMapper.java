package com.curriculum.util;

import com.curriculum.dto.Topic;
import com.curriculum.entity.TopicEntity;
import com.curriculum.entity.UnitEntity;

public class TopicMapper {
	public static TopicEntity mapTopic(Topic topic) {
		TopicEntity topicEntity=new TopicEntity();
		topicEntity.setId(topic.getId());
		topicEntity.setName(topic.getName());
		UnitEntity unitEntity=new UnitEntity();
		unitEntity.setUnitNo(topic.getUnit().getUnitNo());
		topicEntity.setUnit(unitEntity);
		return topicEntity;
	}
}

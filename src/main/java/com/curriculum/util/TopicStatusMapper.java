package com.curriculum.util;

import com.curriculum.dto.TopicStatus;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.entity.TopicEntity;
import com.curriculum.entity.TopicStatusEntity;

public class TopicStatusMapper {
	public static TopicStatusEntity mapTopicStatus(TopicStatus topicStatus)
	{
		TopicStatusEntity topicStatusEntity=new TopicStatusEntity();
		topicStatusEntity.setId(topicStatus.getId());
		topicStatusEntity.setBeginDate(topicStatus.getBeginDate());
		topicStatusEntity.setStatus(topicStatus.getStatus());
		topicStatusEntity.setCompletedDate(topicStatus.getCompletedDate());
		topicStatusEntity.setRemarks(topicStatus.getRemarks());
		TopicEntity topicEntity=new TopicEntity();
		topicEntity.setUnitNo(topicStatus.getTopic().getUnitNo());
		topicStatusEntity.setTopic(topicEntity);
		TeacherEntity teacherEntity=new TeacherEntity();
		teacherEntity.setId(topicStatus.getTeacher().getId());
		topicStatusEntity.setTeacher(teacherEntity);
		ClassEntity classEntity=new ClassEntity();
		classEntity.setRoomNo(topicStatus.getClassDetail().getRoomNo());
		topicStatusEntity.setClassDetail(classEntity);
		return topicStatusEntity;
	}
}

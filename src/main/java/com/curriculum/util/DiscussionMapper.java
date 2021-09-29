package com.curriculum.util;

import com.curriculum.dto.Discussion;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.DiscussionEntity;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.entity.TopicEntity;

public class DiscussionMapper {
	public static DiscussionEntity discussionMapper(Discussion discussion) {
		DiscussionEntity discussionEntity = new DiscussionEntity();
		discussionEntity.setQuestionNo(discussion.getQuestionNo());
		discussionEntity.setQuestion(discussion.getQuestion());
		discussionEntity.setAnswer(discussion.getAnswer());
		discussionEntity.setDate(discussion.getDate());
		TopicEntity topicEntity = new TopicEntity();
		topicEntity.setUnitNo(discussion.getTopic().getUnitNo());
		discussionEntity.setTopic(topicEntity);
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setId(discussion.getTeacher().getId());
		discussionEntity.setTeacher(teacherEntity);
		ClassEntity classEntity=new ClassEntity();
		classEntity.setRoomNo(discussion.getClassDetail().getRoomNo());
		discussionEntity.setClassDetail(classEntity);
		return discussionEntity;
	}
}

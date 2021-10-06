package com.curriculum.util;

import com.curriculum.dto.Discussion;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.DiscussionEntity;
import com.curriculum.entity.TeacherEntity;
import com.curriculum.entity.UnitEntity;

public class DiscussionMapper {
	public static DiscussionEntity discussionMapper(Discussion discussion) {
		DiscussionEntity discussionEntity = new DiscussionEntity();
		discussionEntity.setQuestionNo(discussion.getQuestionNo());
		discussionEntity.setQuestion(discussion.getQuestion());
		discussionEntity.setAnswer(discussion.getAnswer());
		discussionEntity.setDate(discussion.getDate());
		UnitEntity unitEntity = new UnitEntity();
		unitEntity.setUnitNo(discussion.getUnit().getUnitNo());
		discussionEntity.setUnit(unitEntity);
		TeacherEntity teacherEntity = new TeacherEntity();
		teacherEntity.setId(discussion.getTeacher().getId());
		discussionEntity.setTeacher(teacherEntity);
		ClassEntity classEntity = new ClassEntity();
		classEntity.setRoomNo(discussion.getClassDetail().getRoomNo());
		discussionEntity.setClassDetail(classEntity);
		return discussionEntity;
	}
}

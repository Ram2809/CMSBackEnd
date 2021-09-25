package com.curriculum.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import com.curriculum.entity.SubjectAssignEntity;
import com.curriculum.entity.TopicEntity;

import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class Subject {
	@NotNull
	@Size(max = 6)
	private String code;
	@NotNull
	private String name;
	private Set<SubjectAssignEntity> subjects;
	private Set<TopicEntity> topics;
}

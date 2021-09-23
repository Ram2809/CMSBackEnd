package com.curriculum.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Subject {
	@NotNull
	@Size(max=6)
	private String code;
	@NotNull
	private String name;
	private Class classRoom;
	private Set<TeacherSubject> subjects;
	private Set<Topic> topics;
}

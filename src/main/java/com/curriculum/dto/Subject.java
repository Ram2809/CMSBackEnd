package com.curriculum.dto;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Subject {
	private String code;
	private String name;
	private Class classRoom;
	private Set<TeacherSubject> subjects;
	private Set<Topic> topics;
}

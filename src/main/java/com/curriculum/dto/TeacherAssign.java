package com.curriculum.dto;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherAssign {
	private Long id;
	private Teacher teacher;
	private SubjectAssign subjectAssign;
	private Set<TeacherAssign> teacherAssign;
}

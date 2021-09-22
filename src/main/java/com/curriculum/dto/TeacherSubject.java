package com.curriculum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherSubject {
	private Long id;
	private Teacher teacher;
	private Subject subject;
}

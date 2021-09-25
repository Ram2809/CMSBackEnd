package com.curriculum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TeacherAssign {
	private Long id;
	private Teacher teacher;
	private Subject subject;
}

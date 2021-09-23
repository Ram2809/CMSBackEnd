package com.curriculum.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Class {
	@NotNull
	private Long roomNo;
	@NotNull
	private String standard;
	@NotNull
	private String section;
	private Set<Student> student;
	private Set<Subject> subject;
	private Set<TimeTable> timeTable;
}

package com.curriculum.dto;

import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Class {
	private Long roomNo;
	private String standard;
	private String section;
	private Set<Student> student;
	private Set<Subject> subject;
	private Set<TimeTable> timeTable;
}

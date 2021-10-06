package com.curriculum.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Class {
	@NotNull
	private Long roomNo;
	@NotNull
	@Size(max = 3, message = "Standard cannot be empty and Standard not more than 3")
	private String standard;
	@NotNull
	@Size(max = 2, message = "Section cannot be empty and Section length not more than 2")
	private String section;
	private Set<Student> student;
	private Set<Subject> subject;
	private Set<TimeTable> timeTable;
	private Set<Discussion> discussion;
	public Class(@NotNull Long roomNo,
			@NotNull @Size(max = 3, message = "Standard cannot be empty and Standard not more than 3") String standard,
			@NotNull @Size(max = 2, message = "Section cannot be empty and Section length not more than 2") String section) {
		super();
		this.roomNo = roomNo;
		this.standard = standard;
		this.section = section;
	}
}

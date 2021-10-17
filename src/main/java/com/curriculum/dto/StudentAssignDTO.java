package com.curriculum.dto;

import java.time.LocalDate;
import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class StudentAssignDTO {
	private Long id;
	@NotNull
	@NotBlank
	private String academicYear;
	private LocalDate studentAddedOn=LocalDate.now();
	private LocalDate studentLeftOn;
	private Student student;
	private Class classDetail;
}

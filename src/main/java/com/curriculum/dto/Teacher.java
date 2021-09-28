package com.curriculum.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Teacher {
	private Long id;
	private String firstName;
	private String lastName;
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	private String gender;
	private String qualification;
	private String major;
	@Email
	private String email;
	@Digits(integer = 10, fraction = 0)
	private Long contactNo;
	private Set<TeacherAssign> teachers;
	private Login login;
}

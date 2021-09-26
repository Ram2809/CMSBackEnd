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
	@NotNull
	private Long id;
	@NotNull
	@NotBlank
	private String firstName;
	private String lastName;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	@NotNull
	@NotBlank
	private String gender;
	@NotNull
	@NotBlank
	private String qualification;
	@NotNull
	@NotBlank
	private String major;
	@NotNull
	@Email
	private String email;
	@NotNull
	@Digits(integer = 10, fraction = 0)
	private Long contactNo;
	@NotNull
	@NotBlank
	private String address;
	private Set<TeacherAssign> teachers;
	private Login login;
}

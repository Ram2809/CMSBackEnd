package com.curriculum.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class HeadMaster {
	@NotNull
	private Long id;
	@NotNull
	private String firstName;
	@NotNull
	private String lastName;
	@NotNull
	@Temporal(TemporalType.DATE)
	private Date dateOfBirth;
	@NotNull
	private String gender;
	@NotNull
	private String qualification;
	@NotNull
	private String major;
	@NotNull
	@Email
	private String email;
	@NotNull
	@Digits(integer=10,fraction=0)
	private Long contactNo;
	@NotNull
	private String address;
}

package com.curriculum.dto;

import javax.validation.constraints.NotNull;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class Address {
	private Long id;
	@NotNull
	private String addressLine;
	private String city;
	@NotNull
	private String district;
	@NotNull
	private String state;
	@NotNull
	private String country;
	@NotNull
	private Long pinCode;
	private Teacher teacher;
}

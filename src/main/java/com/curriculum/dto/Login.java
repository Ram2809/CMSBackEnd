package com.curriculum.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Login {
	private Long loginId;
	@NotNull
	@Size(min=8)
	private String password;
	private Teacher teacher;
}

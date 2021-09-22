package com.curriculum.dto;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Login {
	private Long loginId;
	private String password;
	private Teacher teacher;
}

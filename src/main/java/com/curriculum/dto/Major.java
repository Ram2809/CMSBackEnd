package com.curriculum.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Major {
	private Long id;
	@NotNull
	@NotBlank
	private String name;
}

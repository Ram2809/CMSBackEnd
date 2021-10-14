package com.curriculum.dto;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Qualification {
	private Long id;
	@NotNull
	@NotBlank
	@Size(max = 10)
	private String name;
}

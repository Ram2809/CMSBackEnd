package com.curriculum.dto;

import java.util.Set;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Topic {
	@Size(max = 8, message = "Unit Number not exceeds the 8 characters!")
	private String unitNo;
	@NotNull
	private String unitName;
	@NotNull
	private String description;
	private String month;
	private Subject subject;
	private Set<Discussion> discussion;
}

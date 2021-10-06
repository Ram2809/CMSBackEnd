package com.curriculum.dto;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class SubjectAssign {
	private Long id;
	private Class classDetail;
	private Subject subject;
}

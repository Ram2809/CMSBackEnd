package com.curriculum.dto;

import java.util.Date;
import java.util.Set;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Topic {
	private String unitNo;
	private String unitName;
	private String description;
	private Date beginDate;
	private String status;
	private Date endDate;
	private Subject subject;
	private Set<Discussion> discussion;
}

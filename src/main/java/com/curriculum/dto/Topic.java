package com.curriculum.dto;

import java.util.Date;
import java.util.Set;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
	@Size(max = 200, message = "Description only contains 200 characters!")
	private String description;
	@Temporal(TemporalType.DATE)
	private Date beginDate;
	private String status;
	@Temporal(TemporalType.DATE)
	private Date endDate;
	private Subject subject;
	private Set<Discussion> discussion;
}

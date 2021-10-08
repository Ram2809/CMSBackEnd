package com.curriculum.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UnitStatus {
	private Long id;
	@Temporal(TemporalType.DATE)
	private Date beginDate;
	private String status;
	@Temporal(TemporalType.DATE)
	private Date completedDate;
	private String remarks;
	private Unit unit;
	private Teacher teacher;
	private Class classDetail;
}
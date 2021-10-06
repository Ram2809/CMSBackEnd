package com.curriculum.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Discussion {
	private Long questionNo;
	private String question;
	private String answer;
	@Temporal(TemporalType.DATE)
	private Date date;
	private Unit unit;
	private Teacher teacher;
	private Class classDetail;
}

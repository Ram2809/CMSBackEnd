package com.curriculum.dto;

import java.util.Date;

import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Discussion {
	private Long questionNo;
	@NotNull
	private String question;
	@NotNull
	private String answer;
	@Temporal(TemporalType.DATE)
	private Date date;
	private Topic topic;
	private Teacher teacher;
}

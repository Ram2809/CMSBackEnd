package com.curriculum.dto;

import java.util.Date;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Discussion {
	private Long questionNo;
	private String question;
	private String answer;
	private Date date;
	private Topic topic;
}

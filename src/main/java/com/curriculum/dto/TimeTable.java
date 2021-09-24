package com.curriculum.dto;

import java.util.Map;

import javax.validation.constraints.NotNull;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeTable {
	private Long id;
	@NotNull
	private String day;
	private Map<Integer,String> periods;
	private Class classRoom;
}

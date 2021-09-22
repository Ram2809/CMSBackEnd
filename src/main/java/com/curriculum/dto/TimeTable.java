package com.curriculum.dto;

import java.util.Map;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class TimeTable {
	private Long id;
	private String day;
	private Map<Integer,String> periods;
	private Class classRoom;
}

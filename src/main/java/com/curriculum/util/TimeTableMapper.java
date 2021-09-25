package com.curriculum.util;

import java.util.Map;

import com.curriculum.dto.TimeTable;
import com.curriculum.entity.ClassEntity;
import com.curriculum.entity.TimeTableEntity;

public class TimeTableMapper {
	public static TimeTableEntity timeTableMapper(TimeTable timeTable) {
		TimeTableEntity timeTableEntity = new TimeTableEntity();
		timeTableEntity.setId(timeTable.getId());
		timeTableEntity.setDay(timeTable.getDay());
		Map<Integer, String> periodsMap = timeTable.getPeriods();
		timeTableEntity.setPeriods(periodsMap);
		ClassEntity classEntity = new ClassEntity();
		classEntity.setRoomNo(timeTable.getClassRoom().getRoomNo());
		timeTableEntity.setClassRoom(classEntity);
		return timeTableEntity;
	}
}

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
		System.out.println(timeTable);
		ClassEntity classEntity = new ClassEntity();
		System.out.println(timeTable.getClassDetail().getRoomNo());
		classEntity.setRoomNo(timeTable.getClassDetail().getRoomNo());
		timeTableEntity.setClassRoom(classEntity);
		return timeTableEntity;
	}
}

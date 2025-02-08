package com.wypl.jpacalendardomain.schedule.mapper;

import com.wypl.jpacalendardomain.schedule.data.ConvertibleScheduleInfo;
import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.schedule.domain.ScheduleInfo;

public class ScheduleInfoMapper {

	public static ScheduleInfo toJpaScheduleInfo(Calendar calendar, ConvertibleScheduleInfo scheduleInfo) {
		return ScheduleInfo.builder()
			.creatorId(scheduleInfo.getCreatorId())
			.calendar(calendar)
			.build();
	}

	public static ScheduleInfo toJpaScheduleInfo(Calendar calendar, long id) {
		return ScheduleInfo.builder()
			.creatorId(id)
			.calendar(calendar)
			.build();
	}
}

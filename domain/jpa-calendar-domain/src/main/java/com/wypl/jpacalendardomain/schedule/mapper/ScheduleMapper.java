package com.wypl.jpacalendardomain.schedule.mapper;

import com.wypl.jpacalendardomain.schedule.data.ConvertibleSchedule;
import com.wypl.jpacalendardomain.schedule.domain.Schedule;
import com.wypl.jpacalendardomain.schedule.domain.ScheduleInfo;
import com.wypl.jpacalendardomain.schedule.domain.embedded.Repetition;

public class ScheduleMapper {

	public static Schedule toJpaSchedule(ConvertibleSchedule convertibleSchedule, ScheduleInfo scheduleInfo) {
		Repetition repetition = Repetition.builder()
			.startDate(convertibleSchedule.getRepetitionStartDate())
			.endDate(convertibleSchedule.getRepetitionEndDate())
			.cycle(convertibleSchedule.getRepetitionCycle())
			.weekInterval(convertibleSchedule.getWeekInterval())
			.dayOfWeek(convertibleSchedule.getDayOfWeek())
			.build();
		return Schedule.builder()
			.scheduleInfo(scheduleInfo)
			.title(convertibleSchedule.getTitle())
			.description(convertibleSchedule.getDescription())
			.startDateTime(convertibleSchedule.getStartDateTime())
			.endDateTime(convertibleSchedule.getEndDateTime())
			.repetition(repetition)
			.build();
	}
}

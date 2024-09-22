package com.wypl.jpacalendardomain.calendar.mapper;

import com.wypl.jpacalendardomain.calendar.data.ConvertibleSchedule;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpacalendardomain.calendar.domain.ScheduleInfo;

public class ScheduleMapper {

    public static Schedule toJpaSchedule(ConvertibleSchedule convertibleSchedule, ScheduleInfo scheduleInfo) {
        return Schedule.builder()
                .scheduleInfo(scheduleInfo)
                .title(convertibleSchedule.getTitle())
                .description(convertibleSchedule.getDescription())
                .startDateTime(convertibleSchedule.getStartDateTime())
                .endDateTime(convertibleSchedule.getEndDateTime())
                .repetitionStartDate(convertibleSchedule.getRepetitionStartDate())
                .repetitionEndDate(convertibleSchedule.getRepetitionEndDate())
                .repetitionCycle(convertibleSchedule.getRepetitionCycle())
                .dayOfWeek(convertibleSchedule.getDayOfWeek())
                .weekInterval(convertibleSchedule.getWeekInterval())
                .build();
    }
}

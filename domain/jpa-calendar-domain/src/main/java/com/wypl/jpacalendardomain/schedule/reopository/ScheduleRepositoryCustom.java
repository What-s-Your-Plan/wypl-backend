package com.wypl.jpacalendardomain.schedule.reopository;

import java.time.LocalDate;
import java.util.List;

import com.wypl.jpacalendardomain.schedule.domain.Schedule;

public interface ScheduleRepositoryCustom {
	List<Schedule> findByCalendarIdAndBetweenStartDateAndEndDate(long calendarId, LocalDate startDate,
		LocalDate endDate);
}

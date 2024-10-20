package com.wypl.jpacalendardomain.calendar.repository;

import java.time.LocalDate;
import java.util.List;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;

public interface ScheduleRepositoryCustom {
	List<Schedule> findByCalendarIdAndBetweenStartDateAndEndDate(long calendarId, LocalDate startDate, LocalDate endDate);
}

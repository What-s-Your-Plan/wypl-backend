package com.wypl.wyplcore.calendar.service.strategy;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Component;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleRepository;
import com.wypl.wyplcore.schedule.data.CalendarType;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.RepetitionService;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class MonthCalendarStrategy implements CalendarStrategy {

	private final ScheduleRepository scheduleRepository;

	@Override
	public CalendarType getCalendarType() {
		return CalendarType.WEEK;
	}

	@Override
	public List<ScheduleFindResponse> getAllSchedule(long calendarId, LocalDate startDate) {

		List<ScheduleFindResponse> scheduleFindResponses = new ArrayList<>();
		startDate = startDate.withDayOfMonth(1);
		LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

		List<Schedule> schedules = scheduleRepository.findByCalendarIdAndBetweenStartDateAndEndDate(calendarId, startDate, endDate);

		for (Schedule schedule : schedules) {
			scheduleFindResponses.addAll(RepetitionService.getScheduleResponses(schedule, startDate, endDate));
		}
		return scheduleFindResponses;
	}
}

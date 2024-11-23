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

	@Override
	public CalendarType getCalendarType() {
		return CalendarType.MONTH;
	}

	/**
	 * Month 달력의 전체 일정 조회한다.
	 * @param calendarId
	 * @param startDate
	 * @return List<ScheduleFindResponse>
	 */
	@Override
	public List<ScheduleFindResponse> getAllSchedule(ScheduleRepository scheduleRepository, long calendarId, LocalDate startDate) {

		LocalDate searchStartDate = startDate.withDayOfMonth(1);
		LocalDate searchEndDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

		List<Schedule> schedules = scheduleRepository.findByCalendarIdAndBetweenStartDateAndEndDate(calendarId, searchStartDate, searchEndDate);

		List<ScheduleFindResponse> scheduleFindResponses = new ArrayList<>();
		for (Schedule schedule : schedules) {
			scheduleFindResponses.addAll(RepetitionService.getScheduleResponses(schedule, searchStartDate, searchEndDate));
		}
		return scheduleFindResponses;
	}

}

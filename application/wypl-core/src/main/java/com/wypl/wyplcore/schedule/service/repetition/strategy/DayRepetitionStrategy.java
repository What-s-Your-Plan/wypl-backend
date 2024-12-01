package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.wyplcore.calendar.service.CalendarServiceUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

public class DayRepetitionStrategy implements RepetitionStrategy {

	/**
	 * RepetitionCycle이 Day일 때 Schedule의 반복 일정을 조회한다.
	 * @param schedule
	 * @param searchStartDate
	 * @param searchEndDate
	 * @return List<ScheduleFindResponse>
	 */
	@Override
	public List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate searchStartDate,
		LocalDate searchEndDate) {

		List<ScheduleFindResponse> responses = new ArrayList<>();

		searchStartDate = getMaxDate(searchStartDate, schedule.getRepetitionStartDate());
		searchEndDate = getMinDate(searchEndDate, schedule.getRepetitionEndDate());

		for (; !searchStartDate.isAfter(searchEndDate); searchStartDate = searchStartDate.plusDays(1)) {
			LocalDateTime startDateTime = LocalDateTime.of(searchStartDate, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = LocalDateTime.of(searchStartDate, schedule.getEndDateTime().toLocalTime());
			responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
		}
		return responses;
	}
}

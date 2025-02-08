package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.wyplcore.calendar.service.CalendarServiceUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

public class DayRepetitionStrategy implements RepetitionStrategy {

	/**
	 * RepetitionCycle = Day 인 경우, Schedule 반복 일정을 조회한다.
	 * @param schedule 할일
	 * @param searchStartDate 검색 시작일자
	 * @param searchEndDate 검색 종료일자
	 * @return List<ScheduleFindResponse>
	 */
	@Override
	public List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate searchStartDate,
		LocalDate searchEndDate) {

		searchStartDate = getMaxDate(searchStartDate, schedule.getRepetitionStartDate());
		searchEndDate = getMinDate(searchEndDate, schedule.getRepetitionEndDate());

		return searchStartDate.datesUntil(searchEndDate.plusDays(1)).map(
			date -> {
			LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = LocalDateTime.of(date, schedule.getEndDateTime().toLocalTime());
			return ScheduleFindResponse.of(schedule, startDateTime, endDateTime);
		}).collect(Collectors.toList());
	}
}

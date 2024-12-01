package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.wyplcore.calendar.service.CalendarServiceUtil.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

public class MonthRepetitionStrategy implements RepetitionStrategy{

	/**
	 * RepetitionCycle이 Month일 때 Schedule의 반복 일정을 조회한다.
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

		// 끝나는 날짜
		int endDayOfMonth = schedule.getEndDateTime().getDayOfMonth();

		// 탐색할 시작 일시와 끝 일시 설정
		LocalDateTime searchEndDateTime = LocalDateTime.of(searchStartDate.withDayOfMonth(endDayOfMonth), schedule.getEndDateTime().toLocalTime());
		Duration duration = Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime());
		LocalDateTime searchStartDateTime = searchEndDateTime.minus(duration);

		for ( LocalDate date = searchStartDateTime.toLocalDate(); !date.isAfter(searchEndDate); date = date.plusMonths(1)) {
			LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = startDateTime.plus(duration);
			responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
		}
		return responses;
	}
}

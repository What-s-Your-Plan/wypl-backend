package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.common.utils.DateUtil.*;
import static com.wypl.wyplcore.calendar.service.CalendarServiceUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.List;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

public class MonthRepetitionStrategy implements RepetitionStrategy {

	/**
	 * RepetitionCycle = Month 인 경우, Schedule 반복 일정을 조회한다.
	 * @param schedule target schedule entity
	 * @param searchStartDate 검색 시작일
	 * @param searchEndDate 검색 종료일
	 * @return List<ScheduleFindResponse> 반복 일정 리스트
	 */
	@Override
	public List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate searchStartDate,
		LocalDate searchEndDate) {

		searchStartDate =  getMaxDate(searchStartDate, schedule.getRepetitionStartDate());
		searchEndDate = getMinDate(searchEndDate, schedule.getRepetitionEndDate());
		LocalDate startDate = getFirstScheduleStartDate(schedule, searchStartDate);

		return startDate.datesUntil(searchEndDate.plusDays(1), Period.ofMonths(1))
			.map(date -> {
				LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
				LocalDateTime endDateTime = startDateTime.plus(schedule.getDuration());
				return ScheduleFindResponse.of(schedule, startDateTime, endDateTime);
			}).toList();
	}

	/**
	 * 검색 조건 범위에 포함되는 첫 번째 Schedule 의 시작일자를 찾는다.
	 * @param schedule target schedule entity
	 * @param searchStartDate 검색 시작일
	 * @return 색 조건 범위에 포함되는 첫 번째 Schedule 의 시작일자
	 */
	private LocalDate getFirstScheduleStartDate(Schedule schedule, LocalDate searchStartDate) {

		LocalDate firstScheduleEndDate = findNextOrSameByDayOfMonth(searchStartDate, schedule.getEndDateTime().getDayOfMonth());

		LocalDateTime firstScheduleEndDateTime = LocalDateTime.of(firstScheduleEndDate, schedule.getEndDateTime().toLocalTime());

		return firstScheduleEndDateTime.minus(schedule.getDuration()).toLocalDate();

	}
}

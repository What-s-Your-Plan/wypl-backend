package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.wyplcore.calendar.service.CalendarServiceUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

		LocalDate startDate = getStartDate(schedule, getMaxDate(searchStartDate, schedule.getRepetitionStartDate()));
		searchEndDate = getMinDate(searchEndDate, schedule.getRepetitionEndDate());

		List<ScheduleFindResponse> responses = new ArrayList<>();
		for (LocalDate date = startDate; !date.isAfter(searchEndDate); date = date.plusMonths(1)) {
			LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = startDateTime.plus(schedule.getDuration());
			responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
		}
		return responses;
	}

	/**
	 * 검색 조건 범위에 포함되는 첫 번째 Schedule 의 시작일자를 찾는다.
	 * @param schedule target schedule entity
	 * @param searchStartDate 검색 시작일
	 * @return 색 조건 범위에 포함되는 첫 번째 Schedule 의 시작일자
	 */
	private LocalDate getStartDate(Schedule schedule, LocalDate searchStartDate) {

		LocalDate searchEndDate = findNextDayOfMonth(searchStartDate, schedule.getEndDateTime().getDayOfMonth());

		LocalDateTime searchEndDateTime = LocalDateTime.of(searchEndDate, schedule.getEndDateTime().toLocalTime());

		return searchEndDateTime.minus(schedule.getDuration()).toLocalDate();

	}

	/**
	 * 검색 조건(시작일) 이후에 포함되는 Schedule 의 끝나는 날짜를 찾는다.
	 * @param searchStartDate 검색할 조건의 시작일자
	 * @param endDayOfMonth 일정이 끝나는 날짜 ex) 3일 ~ 5일 지속되는 하나의 일정일 때, 5
	 * @return 검색 조건(시작일) 이후에 포함되는 첫 번째 Schedule 의 끝나는 날짜
	 */
	public static LocalDate findNextDayOfMonth(LocalDate searchStartDate, int endDayOfMonth) {
		// 현재 날짜의 일을 가져오기
		int currentDayOfMonth = searchStartDate.getDayOfMonth();

		if (currentDayOfMonth <= endDayOfMonth) {
			return searchStartDate.withDayOfMonth(endDayOfMonth);
		}
		return searchStartDate.plusMonths(1).withDayOfMonth(endDayOfMonth);
	}
}

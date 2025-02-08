package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.common.utils.DateUtil.*;
import static com.wypl.wyplcore.calendar.service.CalendarServiceUtil.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

public class WeekRepetitionStrategy implements RepetitionStrategy {

	/**
	 * RepetitionCycle = Week 인 경우, Schedule 반복 일정을 조회한다.
	 * @param schedule Target Schedule
	 * @param searchStartDate 검색 시작일자
	 * @param searchEndDate 검색 종료일자
	 * @return {List<ScheduleFindResponse>}
	 */
	@Override
	public List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate searchStartDate,
		LocalDate searchEndDate) {

		searchStartDate = getMaxDate(searchStartDate, schedule.getRepetitionStartDate()); // 검색 범위의 시작일
		searchEndDate = getMinDate(searchEndDate, schedule.getRepetitionEndDate());

		List<ScheduleFindResponse> responses = new ArrayList<>();

		if (!schedule.existsDayOfWeek()) { // 반복 요일을 설정하지 않았을 경우

			LocalDate firstScheduleStartDate = getFirstScheduleStartDate(searchStartDate, schedule); // 검색 조건에 부합하는 첫 번째 일정의 시작일

			for (LocalDate date = firstScheduleStartDate; date.isBefore(searchEndDate); date = date.plusWeeks(
				schedule.getWeekInterval())) {
				LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
				LocalDateTime endDateTime = startDateTime.plus(schedule.getDuration());
				responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
			}
			return responses;

		} else { // 반복 요일을 설정했을 경우

			int repetitionDayOfWeek = schedule.getDayOfWeek();

			for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) { // 요일마다 처리
				if (isSelectedDayOfWeek(repetitionDayOfWeek, dayOfWeek)) {
					LocalDate nearestDate = findNextOrSameByDayOfWeek(searchStartDate, DayOfWeek.of(dayOfWeek));
					for (LocalDate date = nearestDate; date.isBefore(searchEndDate.plusDays(1)); date = date.plusWeeks(schedule.getWeekInterval())) {

						responses.add(ScheduleFindResponse.of(schedule, date, date));
					}
				}
			}
			responses.sort(Comparator.comparing(ScheduleFindResponse::startDateTime));
			return responses;
		}
	}

	/**
	 * searchStartDate 이후 주의 반복 주기를 고려한 첫 번째 일정의 시작일을 찾는다.
	 * @param searchStartDate 검색 시작 날짜
	 * @param schedule 일정
	 * @return 검색된 첫 번째 일정의 시작일
	 */
	private LocalDate getFirstScheduleStartDate(LocalDate searchStartDate, Schedule schedule){

		LocalDate scheduleStartDate = schedule.getStartDateTime().toLocalDate();
		LocalDate unadjustedStartDate = findNearestStartDate(searchStartDate, schedule); // WeekInterval 반영되지 않은 상태

		// 주의 차이를 계산
		long weeksBetween = ChronoUnit.DAYS.between(scheduleStartDate, unadjustedStartDate) / 7;
		int weekInterval = schedule.getWeekInterval();

		if (weeksBetween % weekInterval == 0) { // searchStartDate 가 반복 주기에 포함되는 Week 인 경우
			return unadjustedStartDate;
		}

		long addWeek = (weeksBetween / weekInterval + 1) * weekInterval - weeksBetween; // 반복 주기에 포함되도록 더해야 할 Week

		return unadjustedStartDate.plusWeeks(addWeek);

	}

	/**
	 * 검색시작일자(searchStartDate)에 부합하는 가장 가까운 일정시작요일(dayOfWeek of startDateTime of schedule)을 검색한다.
	 * @param searchStartDate 검색시작일자
	 * @param schedule 일정
	 * @return LocalDateTime 검색 조건과 일정의 시작 및 끝나는 요일에 부합하는 가장 가까운 시작일시
	 */
	private LocalDate findNearestStartDate(LocalDate searchStartDate, Schedule schedule) {

		// 검색 시작일자 이후, 끝나는 요일과 같은 가장 가까운 날짜
		LocalDate nearestEndDate = findNextOrSameByDayOfWeek(searchStartDate, schedule.getEndDateTime().getDayOfWeek());

		LocalDateTime nearestEndDateTime = LocalDateTime.of(nearestEndDate, schedule.getEndDateTime().toLocalTime());

		return nearestEndDateTime.minus(schedule.getDuration()).toLocalDate();
	}

	/**
	 * dayOfWeek 가 repetitionDayOfWeek에 포함됐는지 확인한다.
	 * @param repetitionDayOfWeek 일정의 반복 요일을 비트마스킹한 값
	 * @param dayOfWeek from 1 to 7 (1: 월요일, 2: 화요일, 3: 수요일, 4: 목요일, 5: 금요일, 6: 토요일, 7: 일요일)
	 * @return boolean
	 */
	private boolean isSelectedDayOfWeek(int repetitionDayOfWeek, int dayOfWeek) {
		return (repetitionDayOfWeek & (1 << dayOfWeek)) != 0;
	}
}

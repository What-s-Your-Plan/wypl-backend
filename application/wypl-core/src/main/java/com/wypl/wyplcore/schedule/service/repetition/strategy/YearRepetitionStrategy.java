package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.common.utils.DateUtil.*;
import static com.wypl.wyplcore.calendar.service.CalendarServiceUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wypl.jpacalendardomain.schedule.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

@Service
public class YearRepetitionStrategy implements RepetitionStrategy {

	/**
	 * searchStartDate 와 같거나 그 이후의 첫 번째 일정 시작일을 찾는다.
	 * @param schedule 할일
	 * @param searchStartDate 검색 시작 일자
	 * @return LocalDateTime
	 */
	private static LocalDate getFirstScheduleStartDate(Schedule schedule, LocalDate searchStartDate) {
		LocalDate firstScheduleEndDate = findNextOrSame(searchStartDate, schedule.getEndDateTime().getMonth(),
			schedule.getEndDateTime().getDayOfMonth());
		LocalDateTime firstScheduleEndDateTime = LocalDateTime.of(firstScheduleEndDate,
			schedule.getEndDateTime().toLocalTime());
		return firstScheduleEndDateTime.minus(schedule.getDuration()).toLocalDate();
	}

	/**
	 * RepetitionCycle = Year 인 경우, Schedule 반복 일정을 조회한다.
	 * @param schedule Target Schedule
	 * @param searchStartDate 검색할 시작일자
	 * @param searchEndDate 검색할 종료일자
	 * @return List<ScheduleFindResponse>
	 */
	@Override
	public List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate searchStartDate,
		LocalDate searchEndDate) {

		searchStartDate = getMaxDate(searchStartDate, schedule.getRepetitionStartDate());
		searchEndDate = getMinDate(searchEndDate, schedule.getRepetitionEndDate());

		LocalDate firstStartScheduleDate = getFirstScheduleStartDate(schedule, searchStartDate);

		if (firstStartScheduleDate.isAfter(searchEndDate)) {
			return new ArrayList<>();
		}

		return firstStartScheduleDate.datesUntil(searchEndDate.plusDays(1), Period.ofYears(1)).map(
			date -> {
				LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
				LocalDateTime endDateTime = startDateTime.plus(schedule.getDuration());
				return ScheduleFindResponse.of(schedule, startDateTime, endDateTime);
			}
		).toList();
	}
}

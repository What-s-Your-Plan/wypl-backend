package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.wyplcore.calendar.service.CalendarServiceUtil.*;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

@Service
public class YearRepetitionStrategy implements RepetitionStrategy{

	/**
	 * RepetitionCycle이 Year일 때 Schedule의 반복 일정을 조회한다.
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

		LocalDateTime nearestStartDateTime = getNearestStartDateTime(schedule, searchStartDate);

		for(LocalDate date = nearestStartDateTime.toLocalDate(); !date.isAfter(searchEndDate); date = date.plusYears(1)) {
			LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = startDateTime.plus(Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime()));
			responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
		}
		return responses;
	}

	/**
	 * searchStartDate 검색 조건에 부합하는 가장 가까운 일정의 시작일자를 반환
	 * @param schedule
	 * @param searchStartDate
	 * @return LocalDateTime
	 */
	private static LocalDateTime getNearestStartDateTime(Schedule schedule, LocalDate searchStartDate) {
		LocalDateTime nearestEndDateTime = LocalDateTime.of(searchStartDate.withDayOfYear(schedule.getEndDateTime().getDayOfYear()), schedule.getEndDateTime().toLocalTime());
		LocalDateTime nearestStartDateTime = nearestEndDateTime.minus(
			Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime()));
		return nearestStartDateTime;
	}

}

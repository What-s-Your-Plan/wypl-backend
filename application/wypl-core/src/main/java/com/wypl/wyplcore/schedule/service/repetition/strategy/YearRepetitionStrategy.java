package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.wyplcore.calendar.service.CalendarServiceUtil.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

@Service
public class YearRepetitionStrategy implements RepetitionStrategy {

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

		LocalDateTime nearestStartDateTime = getNearestStartDateTime(schedule, searchStartDate);

		List<ScheduleFindResponse> responses = new ArrayList<>();
		for (LocalDate date = nearestStartDateTime.toLocalDate(); !date.isAfter(searchEndDate); date = date.plusYears(
			1)) {
			LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = startDateTime.plus(
				schedule.getDuration());
			responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
		}
		return responses;
	}

	/**
	 *
	 * @param schedule 할일
	 * @param searchStartDate
	 * @return LocalDateTime
	 */
	private static LocalDateTime getNearestStartDateTime(Schedule schedule, LocalDate searchStartDate) {
		LocalDateTime nearestEndDateTime = LocalDateTime.of(
			searchStartDate.withDayOfYear(schedule.getEndDateTime().getDayOfYear()),
			schedule.getEndDateTime().toLocalTime());
		LocalDateTime nearestStartDateTime = nearestEndDateTime.minus(
			schedule.getDuration());
		return nearestStartDateTime;
	}

}

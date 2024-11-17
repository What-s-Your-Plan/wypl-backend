package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.wyplcore.calendar.CalendarServiceUtil.*;

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
	@Override
	public List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate searchStartDate,
		LocalDate searchEndDate) {
		List<ScheduleFindResponse> responses = new ArrayList<>();

		searchStartDate = getMaxDate(searchStartDate, schedule.getRepetitionStartDate());
		searchEndDate = getMinDate(searchEndDate, schedule.getRepetitionEndDate());

		// startDate와 같거나 가장 가까운 schedule.endDateTime의 날짜, 시간을 가진 LocalDateTime 생성
		LocalDateTime nearestEndDateTime = LocalDateTime.of(searchStartDate.withDayOfYear(schedule.getEndDateTime().getDayOfYear()), schedule.getEndDateTime().toLocalTime());
		LocalDateTime nearestStartDateTime = nearestEndDateTime.minus(
			Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime()));

		for(LocalDate date = nearestStartDateTime.toLocalDate(); !date.isAfter(searchEndDate); date = date.plusYears(1)) {
			LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = startDateTime.plus(Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime()));
			responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
		}

		return responses;
	}
}

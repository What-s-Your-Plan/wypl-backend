package com.wypl.wyplcore.calendar;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import com.wypl.jpacalendardomain.calendar.data.RepetitionCycle;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

public class CalendarServiceUtil {

	public static List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate startDate, LocalDate endDate) {
		if (!schedule.isRepetition()) return List.of(ScheduleFindResponse.of(schedule, schedule.getStartDateTime(), schedule.getEndDateTime()));

		if(schedule.getRepetitionCycle().equals(RepetitionCycle.DAY)) {
			return getDayRepetitionSchedules(schedule, startDate, endDate);
		}
		if(schedule.getRepetitionCycle().equals(RepetitionCycle.WEEK)) {
			return getWeekRepetitionSchedules(schedule, startDate, endDate);
		}
		if(schedule.getRepetitionCycle().equals(RepetitionCycle.MONTH)) {
			return getMonthRepetitionSchedules(schedule, startDate, endDate);
		}
		if(schedule.getRepetitionCycle().equals(RepetitionCycle.YEAR)) {
			// Todo: return getYearRepetitionSchedule(schedule, startDate);
		}
		throw new IllegalArgumentException("Invalid RepetitionCycle");
	}

	private static List<ScheduleFindResponse> getDayRepetitionSchedules(Schedule schedule, LocalDate startDate, LocalDate endDate) {

		List<ScheduleFindResponse> responses = new ArrayList<>();
		// 시작일자 설정: date = MAX(startDate, repetitionStartDate)
		LocalDate date = startDate.isAfter(schedule.getRepetitionStartDate()) ? startDate : schedule.getRepetitionStartDate();

		for( ; !date.isAfter(endDate); date = date.plusDays(1)) {
			LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = LocalDateTime.of(date, schedule.getEndDateTime().toLocalTime());
			responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
		}
		return responses;
	}

	private static List<ScheduleFindResponse> getWeekRepetitionSchedules(Schedule schedule, LocalDate startDate, LocalDate endDate) {
		List<ScheduleFindResponse> responses = new ArrayList<>();
		if(!schedule.existsDayOfWeek()){ // 반복 요일을 설정하지 않았을 경우
			System.out.println("getWeekRepetitionSchedules > schedule.getDayOfWeek() == null");

			// 시작하는(요일,시간)과 끝나는(요일,시간)을 알아낸다.
			DayOfWeek startDayOfWeek = schedule.getStartDateTime().getDayOfWeek();
			LocalTime startTime = schedule.getStartDateTime().toLocalTime();

			DayOfWeek endDayOfWeek = schedule.getEndDateTime().getDayOfWeek();
			LocalTime endTime = schedule.getEndDateTime().toLocalTime();

			Duration duration = Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime());

			// 반복 탐색할 시작 날짜 설정 : date = MAX(startDate, repetitionStartDate) and 끝나는 요일 기준으로 startDate 재설정
			LocalDate endDateForSearch = getMaxDate(startDate, schedule.getRepetitionStartDate()).with(TemporalAdjusters.nextOrSame(endDayOfWeek)); //.minus(duration);
			LocalDateTime endDateTimeForSearch = LocalDateTime.of(endDateForSearch, endTime); // 탐색을 시작할 끝 일시 설정
			LocalDateTime dateTimeForSearch = endDateTimeForSearch.minus(duration); // 탐색을 시작할 시작 일시 설정

			// 설정한 weekInterval 만큼씩 증가하면서 endDate까지 반복
			for( LocalDate date = dateTimeForSearch.toLocalDate(); !date.isAfter(endDate); date = date.plusWeeks(schedule.getWeekInterval())) {
				LocalDateTime startDateTime = LocalDateTime.of(date, startTime);
				LocalDateTime endDateTime = startDateTime.plus(duration);
				responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
			}
			return responses;
		}
		// 반복 요일을 설정했을 경우
		int repetitionDayOfWeek = schedule.getDayOfWeek();

		for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) {
			if( isSelectedDayOfWeek(repetitionDayOfWeek, dayOfWeek) ) {

				// Find nearest day by dayOfWeek and increase weekInterval
				LocalDate date = getMaxDate(startDate, schedule.getRepetitionStartDate()).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(dayOfWeek)));

				for(; !date.isAfter(endDate); date = date.plusWeeks(schedule.getWeekInterval())) {
					responses.add(ScheduleFindResponse
						.of(schedule, LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime()), LocalDateTime.of(date, schedule.getEndDateTime().toLocalTime())));
				}
			}
		}
		return responses;
	}

	private static List<ScheduleFindResponse> getMonthRepetitionSchedules(Schedule schedule, LocalDate startDate, LocalDate endDate) {
		List<ScheduleFindResponse> responses = new ArrayList<>();

		// 끝나는 날짜
		int endDayOfMonth = schedule.getEndDateTime().getDayOfMonth();

		// 탐색할 시작 일시와 끝 일시 설정
		LocalDateTime searchEndDateTime = LocalDateTime.of(getMaxDate(startDate, schedule.getRepetitionStartDate()).withDayOfMonth(endDayOfMonth), schedule.getEndDateTime().toLocalTime());
		Duration duration = Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime());
		LocalDateTime searchStartDateTime = searchEndDateTime.minus(duration);

		for ( LocalDate date = searchStartDateTime.toLocalDate(); !date.isAfter(getMinDate(endDate, schedule.getRepetitionEndDate())); date = date.plusMonths(1)) {
			LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = startDateTime.plus(duration);
			responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
		}
		return responses;
	}

	/**
	 * repetitionDayOfWeek에 해당하는 요일이 선택되었는지 확인
	 * @param repetitionDayOfWeek of Schedule
	 * @param dayOfWeek (1: 월요일, 2: 화요일, 3: 수요일, 4: 목요일, 5: 금요일, 6: 토요일, 7: 일요일)
	 * @return boolean
	 */
	static boolean isSelectedDayOfWeek(int repetitionDayOfWeek, int dayOfWeek) {
		return (repetitionDayOfWeek & (1 << dayOfWeek)) != 0;
	}

	static LocalDate getMaxDate(LocalDate date1, LocalDate date2) {
		return date1.isBefore(date2) ? date2 : date1;
	}

	static LocalDate getMinDate(LocalDate date1, LocalDate date2) {
		return date1.isBefore(date2) ? date1 : date2;
	}


}

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

	public static List<ScheduleFindResponse>  getScheduleResponses(Schedule schedule, LocalDate startDate, LocalDate endDate) {
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

	private static List<ScheduleFindResponse> getDayRepetitionSchedules(Schedule schedule, LocalDate searchStartDate, LocalDate searchEndDate) {

		List<ScheduleFindResponse> responses = new ArrayList<>();
		searchStartDate = getMaxDate(searchStartDate, schedule.getRepetitionStartDate());
		searchEndDate = getMinDate(searchEndDate, schedule.getRepetitionEndDate());

		for( ; !searchStartDate.isAfter(searchEndDate); searchStartDate = searchStartDate.plusDays(1)) {
			LocalDateTime startDateTime = LocalDateTime.of(searchStartDate, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = LocalDateTime.of(searchStartDate, schedule.getEndDateTime().toLocalTime());
			responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
		}
		return responses;
	}

	private static List<ScheduleFindResponse> getWeekRepetitionSchedules(Schedule schedule, LocalDate searchStartDate, LocalDate searchEndDate) {

		List<ScheduleFindResponse> responses = new ArrayList<>();

		// 검색할 시작일자와 끝일자 설정
		searchEndDate = getMinDate(searchEndDate, schedule.getRepetitionEndDate());
		searchStartDate = getMaxDate(searchStartDate, schedule.getRepetitionStartDate());

		// 반복 주에 포함되도록 가공
		searchStartDate = getNearestDateUsingWeekInterval(searchStartDate, schedule.getWeekInterval(), schedule);

		if(!schedule.existsDayOfWeek()){ // 반복 요일을 설정하지 않았을 경우

			searchStartDate = resetForBeforeStarted(searchStartDate, schedule);
			Duration duration = Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime());

			// 설정한 weekInterval 만큼씩 증가하면서 endDate까지 반복
			for( LocalDate date = searchStartDate; !date.isAfter(searchEndDate); date = date.plusWeeks(schedule.getWeekInterval())) {
				LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
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
				LocalDate date = getMaxDate(searchStartDate, schedule.getRepetitionStartDate()).with(TemporalAdjusters.nextOrSame(DayOfWeek.of(dayOfWeek)));

				for(; !date.isAfter(searchEndDate); date = date.plusWeeks(schedule.getWeekInterval())) {
					responses.add(ScheduleFindResponse
						.of(schedule, LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime()), LocalDateTime.of(date, schedule.getEndDateTime().toLocalTime())));
				}
			}
		}
		return responses;
	}

	/**
	 * 검색 조건에는 포함되지만, 검색이 시작된 날짜보다 이전에 시작한 Schedule 반영
	 * @param searchStartDate
	 * @param schedule
	 * @return searchStartDate
	 * Todo: 일정이 일주일 이상 지속될 경우에는 처리 불가, 생각해 보기
	 */
	private static LocalDate resetForBeforeStarted(LocalDate searchStartDate, Schedule schedule) {

		// 끝나는 요일 찾기
		DayOfWeek endDayOfWeek = schedule.getEndDateTime().getDayOfWeek();
		Duration duration = Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime());

		// 탐색을 시작할 일자 설정
		LocalDate endDateForSearch = searchStartDate.with(TemporalAdjusters.nextOrSame(endDayOfWeek));
		LocalDateTime endDateTimeForSearch = LocalDateTime.of(endDateForSearch, schedule.getEndDateTime().toLocalTime()); // 탐색을 시작할 끝 일시 설정
		LocalDateTime startDateTimeForSearch = endDateTimeForSearch.minus(duration); // 탐색을 시작할 시작 일시 설정

		return startDateTimeForSearch.toLocalDate();
	}

	/**
	 * 검색 시작일자에 WeekInterval을 적용
	 * @param searchStartDate
	 * @param weekInterval
	 * @param schedule
	 * @return searchStartDate
	 */
	private static LocalDate getNearestDateUsingWeekInterval(LocalDate searchStartDate, Integer weekInterval, Schedule schedule){

		// searchStartDate를 해당 주의 월요일로 설정
		LocalDateTime searchStartMonday = LocalDateTime.of(searchStartDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), LocalTime.of(0, 0));
		LocalDateTime scheduleStartMonday = LocalDateTime.of(schedule.getRepetitionStartDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), LocalTime.of(0, 0));

		int gapOfWeek = (int) Duration.between(scheduleStartMonday, searchStartMonday).toDays() / 7;

		if (gapOfWeek % weekInterval == 0) return searchStartDate;

		int addWeek = (gapOfWeek / weekInterval + 1) * weekInterval - gapOfWeek;

		return searchStartDate.plusWeeks(addWeek);
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

	private static List<ScheduleFindResponse> getYearRepetitionSchedule(Schedule schedule, LocalDate startDate, LocalDate endDate) {
		List<ScheduleFindResponse> responses = new ArrayList<>();

		startDate = getMaxDate(startDate, schedule.getRepetitionStartDate());
		endDate = getMinDate(endDate, schedule.getRepetitionEndDate());

		// startDate와 같거나 가장 가까운 schedule.endDateTime의 날짜, 시간을 가진 LocalDateTime 생성
		LocalDateTime nearestEndDateTime = LocalDateTime.of(startDate.withDayOfYear(schedule.getEndDateTime().getDayOfYear()), schedule.getEndDateTime().toLocalTime());
		LocalDateTime nearestStartDateTime = nearestEndDateTime.minus(Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime()));

		for(LocalDate date = nearestStartDateTime.toLocalDate(); !date.isAfter(endDate); date = date.plusYears(1)) {
			LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
			LocalDateTime endDateTime = startDateTime.plus(Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime()));
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

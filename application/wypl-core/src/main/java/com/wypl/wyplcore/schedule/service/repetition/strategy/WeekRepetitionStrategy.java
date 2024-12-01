package com.wypl.wyplcore.schedule.service.repetition.strategy;

import static com.wypl.wyplcore.calendar.service.CalendarServiceUtil.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

public class WeekRepetitionStrategy implements RepetitionStrategy {

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
		LocalDateTime endDateTimeForSearch = searchStartDate.with(TemporalAdjusters.nextOrSame(endDayOfWeek))
			.atTime(schedule.getEndDateTime().toLocalTime());

		return endDateTimeForSearch.minus(duration).toLocalDate();
	}

	/**
	 * 검색 시작일자에 WeekInterval을 적용
	 * @param searchStartDate
	 * @param weekInterval
	 * @param schedule
	 * @return searchStartDate
	 */
	public static LocalDate getNearestDateUsingWeekInterval(LocalDate searchStartDate, Integer weekInterval,
		Schedule schedule) {

		// searchStartDate를 해당 주의 월요일로 설정
		LocalDateTime searchStartMonday = LocalDateTime.of(
			searchStartDate.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)), LocalTime.of(0, 0));
		LocalDateTime scheduleStartMonday = LocalDateTime.of(
			schedule.getRepetitionStartDate().with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY)),
			LocalTime.of(0, 0));

		int gapOfWeek = (int)Duration.between(scheduleStartMonday, searchStartMonday).toDays() / 7;

		if (gapOfWeek % weekInterval == 0)
			return searchStartDate;

		int addWeek = (gapOfWeek / weekInterval + 1) * weekInterval - gapOfWeek;

		return searchStartDate.plusWeeks(addWeek);
	}

	/**
	 * repetitionDayOfWeek(반복요일)에 해당하는 요일이 선택되었는지 확인
	 * @param repetitionDayOfWeek of Schedule
	 * @param dayOfWeek (1: 월요일, 2: 화요일, 3: 수요일, 4: 목요일, 5: 금요일, 6: 토요일, 7: 일요일)
	 * @return boolean
	 */
	static boolean isSelectedDayOfWeek(int repetitionDayOfWeek, int dayOfWeek) {
		return (repetitionDayOfWeek & (1 << dayOfWeek)) != 0;
	}

	/**
	 * RepetitionCycle이 Week일 때 Schedule의 반복 일정을 조회한다.
	 * @param schedule
	 * @param searchStartDate
	 * @param searchEndDate
	 * @return List<ScheduleFindResponse>
	 */
	@Override
	public List<ScheduleFindResponse> getScheduleResponses(Schedule schedule, LocalDate searchStartDate,
		LocalDate searchEndDate) {

		// 검색할 시작일자와 끝일자 설정
		searchEndDate = getMinDate(searchEndDate, schedule.getRepetitionEndDate());
		searchStartDate = getMaxDate(searchStartDate, schedule.getRepetitionStartDate());

		// 반복 주에 포함되도록 가공
		searchStartDate = getNearestDateUsingWeekInterval(searchStartDate, schedule.getWeekInterval(), schedule);
		List<ScheduleFindResponse> responses = new ArrayList<>();

		if (!schedule.existsDayOfWeek()) { // 반복 요일을 설정하지 않았을 경우

			searchStartDate = resetForBeforeStarted(searchStartDate, schedule);
			Duration duration = Duration.between(schedule.getStartDateTime(), schedule.getEndDateTime());

			// 설정한 weekInterval 만큼씩 증가하면서 endDate까지 반복
			for (LocalDate date = searchStartDate; !date.isAfter(searchEndDate); date = date.plusWeeks(
				schedule.getWeekInterval())) {
				LocalDateTime startDateTime = LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime());
				LocalDateTime endDateTime = startDateTime.plus(duration);
				responses.add(ScheduleFindResponse.of(schedule, startDateTime, endDateTime));
			}
			return responses;
		}
		// 반복 요일을 설정했을 경우
		int repetitionDayOfWeek = schedule.getDayOfWeek();

		for (int dayOfWeek = 1; dayOfWeek <= 7; dayOfWeek++) { // 요일마다 처리
			if (isSelectedDayOfWeek(repetitionDayOfWeek, dayOfWeek)) {

				// Find nearest day by dayOfWeek and increase weekInterval
				LocalDate date = getMaxDate(searchStartDate, schedule.getRepetitionStartDate()).with(
					TemporalAdjusters.nextOrSame(DayOfWeek.of(dayOfWeek)));

				for (; !date.isAfter(searchEndDate); date = date.plusWeeks(schedule.getWeekInterval())) {
					responses.add(ScheduleFindResponse
						.of(schedule, LocalDateTime.of(date, schedule.getStartDateTime().toLocalTime()),
							LocalDateTime.of(date, schedule.getEndDateTime().toLocalTime())));
				}
			}
		}
		return responses;
	}
}

package com.wypl.wyplcore.schedule.service.repetition;

import static com.wypl.wyplcore.ScheduleFixture.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.time.Duration;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.ScheduleFixture;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

@SpringBootTest
class WeekRepetitionStrategyTest {

	private static final Logger logger = LoggerFactory.getLogger(MonthRepetitionStrategyTest.class);

	private Schedule weekRepetitionScheduleWithDayOfWeek;
	private Schedule weekRepetitionScheduleWithoutDayOfWeek;

	@BeforeEach
	void setUpWeekRepetitionSchedule() {
		weekRepetitionScheduleWithDayOfWeek = mock(Schedule.class);
		when(weekRepetitionScheduleWithDayOfWeek.getId()).thenReturn(1L);
		when(weekRepetitionScheduleWithDayOfWeek.getTitle()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE.getTitle());
		when(weekRepetitionScheduleWithDayOfWeek.getDescription()).thenReturn(
			ScheduleFixture.WEEKLY_SCHEDULE.getDescription());
		when(weekRepetitionScheduleWithDayOfWeek.getStartDateTime()).thenReturn(
			ScheduleFixture.WEEKLY_SCHEDULE.getStartDateTime());
		when(weekRepetitionScheduleWithDayOfWeek.getEndDateTime()).thenReturn(
			ScheduleFixture.WEEKLY_SCHEDULE.getEndDateTime());
		when(weekRepetitionScheduleWithDayOfWeek.getRepetitionStartDate()).thenReturn(
			ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionStartDate());
		when(weekRepetitionScheduleWithDayOfWeek.getRepetitionEndDate()).thenReturn(
			ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionEndDate());
		when(weekRepetitionScheduleWithDayOfWeek.getRepetitionCycle()).thenReturn(
			ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionCycle());
		when(weekRepetitionScheduleWithDayOfWeek.getDayOfWeek()).thenReturn(
			ScheduleFixture.WEEKLY_SCHEDULE.getDayOfWeek());
		when(weekRepetitionScheduleWithDayOfWeek.getWeekInterval()).thenReturn(
			ScheduleFixture.WEEKLY_SCHEDULE.getWeekInterval());
		when(weekRepetitionScheduleWithDayOfWeek.isRepetition()).thenReturn(true);
		when(weekRepetitionScheduleWithDayOfWeek.existsDayOfWeek()).thenReturn(true);
	}

	@BeforeEach
	void setUpWeekRepetitionScheduleWithoutDayOfWeek() {
		weekRepetitionScheduleWithoutDayOfWeek = mock(Schedule.class);
		when(weekRepetitionScheduleWithoutDayOfWeek.getId()).thenReturn(1L);
		when(weekRepetitionScheduleWithoutDayOfWeek.getTitle()).thenReturn(
			WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getTitle());
		when(weekRepetitionScheduleWithoutDayOfWeek.getDescription()).thenReturn(
			WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getDescription());
		when(weekRepetitionScheduleWithoutDayOfWeek.getStartDateTime()).thenReturn(
			WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getStartDateTime());
		when(weekRepetitionScheduleWithoutDayOfWeek.getEndDateTime()).thenReturn(
			WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getEndDateTime());
		when(weekRepetitionScheduleWithoutDayOfWeek.getRepetitionStartDate()).thenReturn(
			WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionStartDate());
		when(weekRepetitionScheduleWithoutDayOfWeek.getRepetitionEndDate()).thenReturn(
			WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionEndDate());
		when(weekRepetitionScheduleWithoutDayOfWeek.getRepetitionCycle()).thenReturn(
			WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionCycle());
		when(weekRepetitionScheduleWithoutDayOfWeek.getDayOfWeek()).thenReturn(
			WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getDayOfWeek());
		when(weekRepetitionScheduleWithoutDayOfWeek.getWeekInterval()).thenReturn(
			WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getWeekInterval());
		when(weekRepetitionScheduleWithoutDayOfWeek.isRepetition()).thenReturn(true);
		when(weekRepetitionScheduleWithoutDayOfWeek.existsDayOfWeek()).thenReturn(false);
		when(weekRepetitionScheduleWithoutDayOfWeek.getDuration()).thenReturn(
			Duration.between(WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getStartDateTime(),
				WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getEndDateTime()));
	}

	@Test
	@DisplayName("반복 요일이 존재하는 일정의 DAY 조회")
	void getSchedulesResponsesForTodayWithDayOfWeek() {

		// given
		LocalDate startDate = ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionStartDate().with(
			TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			weekRepetitionScheduleWithDayOfWeek, startDate, startDate);

		// then
		assertEquals(1, scheduleResponses.size());
	}

	@Test
	@DisplayName("반복 요일이 존재하는 일정의 WEEK 조회")
	void getSchedulesResponsesForWeekWithDayOfWeek() {

		// given: weekRepetitionSchedule의 시작일 기준 다음 주 조회 (월요일 ~ 일요일)
		LocalDate startDate = ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionStartDate()
			.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
		LocalDate endDate = startDate.plusDays(6);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			weekRepetitionScheduleWithDayOfWeek, startDate, endDate);

		// then
		for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
			logger.debug("title '{}' : {} ({}) ~ {}({})",
				scheduleResponse.title(),
				scheduleResponse.startDateTime(), scheduleResponse.startDateTime().getDayOfWeek(),
				scheduleResponse.endDateTime(), scheduleResponse.endDateTime().getDayOfWeek());
		}
		assertEquals(3, scheduleResponses.size());
	}

	@Test
	@DisplayName("반복 요일이 존재하는 일정의 MONTH 조회")
	void getSchedulesResponsesForMonthWithDayOfWeek() {

		// given: weekRepetitionSchedule의 시작일 기준 월 조회 (2024년 10월)
		LocalDate startDate = ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionStartDate()
			.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			weekRepetitionScheduleWithDayOfWeek, startDate, endDate);

		// then
		for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
			logger.info("title '{}' : {} ({}) ~ {}({})",
				scheduleResponse.title(),
				scheduleResponse.startDateTime(), scheduleResponse.startDateTime().getDayOfWeek(),
				scheduleResponse.endDateTime(), scheduleResponse.endDateTime().getDayOfWeek());
		}
		assertEquals(5, scheduleResponses.size());

	}

	@Test
	@DisplayName("요일이 설정되지 않은 일정의 DAY 검색 조건으로 반복일정 조회")
	void getSchedulesResponsesForTodayWithoutDayOfWeek() {

		// given: Set today after 1 week, and the range is 'RepetitionStartDate 요일 < today < RepetitionEndDate 요일'
		LocalDate today = WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionStartDate()
			.plusDays(1)
			.plusWeeks(2);
		System.out.println("today -> " + today);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			weekRepetitionScheduleWithoutDayOfWeek, today, today);

		// then
		for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
			System.out.println(scheduleResponse.title() + " : " + scheduleResponse.startDateTime() + " - "
				+ scheduleResponse.endDateTime());
		}
		assertEquals(1, scheduleResponses.size());
	}

	@Test
	@DisplayName("요일이 설정되지 않은 일정의 WEEK 검색 조건으로 반복일정 조회")
	void getSchedulesResponsesForWeekWithoutDayOfWeek() {

		// given: Set today after 1 week, and the range is 'RepetitionStartDate 요일 < today < RepetitionEndDate 요일'
		LocalDate startDate = WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionStartDate()
			.with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
		LocalDate endDate = startDate.plusDays(6);
		System.out.println("startDate -> " + startDate + ", endDate -> " + endDate);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			weekRepetitionScheduleWithoutDayOfWeek, startDate, endDate);

		// then
		for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
			System.out.println(scheduleResponse.title() + " : " + scheduleResponse.startDateTime() + " - "
				+ scheduleResponse.endDateTime());
		}
		assertEquals(1, scheduleResponses.size());
	}

	@Test
	@DisplayName("요일이 설정되지 않은 일정의 WEEK 검색 조건으로 반복일정 조회 - 반복 주에 포함되지 않는 조회 조건")
	void getSchedulesResponsesForWeekWithoutDayOfWeek2() {

		// given: Set today after 1 week, and the range is 'RepetitionStartDate 요일 < today < RepetitionEndDate 요일'
		LocalDate startDate = WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionStartDate().plusWeeks(1);
		LocalDate endDate = startDate.plusDays(6);
		Schedule schedule = WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.toObject();

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			schedule, startDate, endDate);

		// then
		for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
			System.out.println(scheduleResponse.title() + " : " + scheduleResponse.startDateTime() + " - "
				+ scheduleResponse.endDateTime());
		}
		assertEquals(0, scheduleResponses.size());
	}

	@Test
	@DisplayName("요일이 설정되지 않은 일정의 MONTH 검색 조건으로 반복일정 조회")
	void getSchedulesResponsesForMonthWithoutDayOfWeek() {

		// given: Set today after 1 week, and the range is 'RepetitionStartDate 요일 < today < RepetitionEndDate 요일'
		LocalDate startDate = WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionStartDate()
			.plusMonths(1)
			.with(TemporalAdjusters.firstDayOfMonth());
		LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
		System.out.println("startDate -> " + startDate + ", endDate -> " + endDate);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			weekRepetitionScheduleWithoutDayOfWeek, startDate, endDate);

		// then
		for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
			System.out.println(scheduleResponse.title() + " : " + scheduleResponse.startDateTime() + " - "
				+ scheduleResponse.endDateTime());
		}
		assertEquals(2, scheduleResponses.size());
	}

}
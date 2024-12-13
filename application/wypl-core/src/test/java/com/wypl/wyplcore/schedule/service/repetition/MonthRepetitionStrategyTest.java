package com.wypl.wyplcore.schedule.service.repetition;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
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

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

@SpringBootTest
class MonthRepetitionStrategyTest {

	private static final Logger logger = LoggerFactory.getLogger(MonthRepetitionStrategyTest.class);
	private Schedule monthRepetitionSchedule;

	@BeforeEach
	public void setLogLevel() {
		LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
		ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger("root");
		rootLogger.setLevel(Level.DEBUG); // 동적으로 로그 레벨 설정
	}

	@BeforeEach
	void setUpMonthRepetitionSchedule() {
		monthRepetitionSchedule = mock(Schedule.class);
		when(monthRepetitionSchedule.getId()).thenReturn(1L);
		when(monthRepetitionSchedule.getTitle()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getTitle());
		when(monthRepetitionSchedule.getDescription()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getDescription());
		when(monthRepetitionSchedule.getStartDateTime()).thenReturn(
			ScheduleFixture.MONTHLY_SCHEDULE.getStartDateTime());
		when(monthRepetitionSchedule.getEndDateTime()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getEndDateTime());
		when(monthRepetitionSchedule.getRepetitionStartDate()).thenReturn(
			ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionStartDate());
		when(monthRepetitionSchedule.getRepetitionEndDate()).thenReturn(
			ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionEndDate());
		when(monthRepetitionSchedule.getRepetitionCycle()).thenReturn(
			ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionCycle());
		when(monthRepetitionSchedule.getDayOfWeek()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getDayOfWeek());
		when(monthRepetitionSchedule.getWeekInterval()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getWeekInterval());
		when(monthRepetitionSchedule.isRepetition()).thenReturn(true);
	}

	@Test
	@DisplayName("반복일정 조회 - Today")
	void getSchedulesResponsesForToday() {

		// given: Set today as the start date of the monthRepetitionSchedule
		LocalDate startDate = ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionStartDate().plusMonths(1).plusDays(1);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			monthRepetitionSchedule, startDate, startDate);

		// then
		assertEquals(1, scheduleResponses.size());

	}

	@Test
	@DisplayName("반복일정 조회 - Month")
	void getSchedulesResponsesForMonth() {

		// given
		LocalDate searchStartDate = ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionStartDate().withDayOfMonth(1);
		LocalDate searchEndDate = searchStartDate.plusMonths(1);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			monthRepetitionSchedule, searchStartDate, searchEndDate);

		// then
		assertEquals(1, scheduleResponses.size());

	}

	@Test
	@DisplayName("반복일정 조회 - Year")
	void getSchedulesResponsesForYear() {

		// given
		LocalDate searchStartDate = ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionStartDate().plusMonths(1).plusDays(1);
		LocalDate searchEndDate = searchStartDate.plusYears(1);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			monthRepetitionSchedule, searchStartDate, searchEndDate);

		// then
		assertEquals(3, scheduleResponses.size());

	}

	@Test
	@DisplayName("반복일정 조회 - 예외 CASE 1: 검색 시작 날짜의 dayOfMonth < 일정 시작 날짜의 dayOfMonth ")
	void getSchedulesResponsesForSpecial1() {

		// given
		LocalDate searchStartDate = ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionStartDate().withDayOfMonth(10);
		LocalDate searchEndDate = searchStartDate.plusMonths(1);
		logger.debug("searchStartDate : {} ~  searchEndDate : {}", searchStartDate, searchEndDate);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			monthRepetitionSchedule, searchStartDate, searchEndDate);

		for (ScheduleFindResponse response : scheduleResponses){
			logger.debug("할일 : {}, {} ~ {}", response.title(), response.startDateTime(), response.endDateTime());
		}

		// then
		assertEquals(1, scheduleResponses.size());

	}

	@Test
	@DisplayName("반복일정 조회 - 예외 CASE 2: 검색 시작 날짜의 dayOfMonth > 일정 시작 날짜의 dayOfMonth ")
	void getSchedulesResponsesForSpecial2() {

		// given
		LocalDate searchStartDate = ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionStartDate().withDayOfMonth(25);
		LocalDate searchEndDate = searchStartDate.plusMonths(1);
		logger.debug("searchStartDate : {} ~  searchEndDate : {}", searchStartDate, searchEndDate);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			monthRepetitionSchedule, searchStartDate, searchEndDate);

		for (ScheduleFindResponse response : scheduleResponses){
			logger.debug("할일 : {}, {} ~ {}", response.title(), response.startDateTime(), response.endDateTime());
		}

		// then
		assertEquals(1, scheduleResponses.size());

	}
}
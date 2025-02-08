package com.wypl.wyplcore.schedule.service.repetition;

import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.jpacalendardomain.schedule.domain.Schedule;
import com.wypl.wyplcore.ScheduleFixture;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.LoggerContext;

@SpringBootTest
class MonthRepetitionStrategyTest {

	private static final Logger logger = LoggerFactory.getLogger(MonthRepetitionStrategyTest.class);
	private final Schedule monthRepetitionSchedule = ScheduleFixture.MONTHLY_SCHEDULE.toObject();

	@BeforeEach
	public void setLogLevel() {
		LoggerContext loggerContext = (LoggerContext)LoggerFactory.getILoggerFactory();
		ch.qos.logback.classic.Logger rootLogger = loggerContext.getLogger("root");
		rootLogger.setLevel(Level.DEBUG); // 동적으로 로그 레벨 설정
	}

	@Test
	@DisplayName("반복 일정 조회 - Today")
	void getSchedulesResponsesForToday() {

		// given
		LocalDate startDate = ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionStartDate().plusMonths(1).plusDays(1);
		logger.debug("조회 조건: {} ~ {}", startDate, startDate);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			monthRepetitionSchedule, startDate, startDate);

		// then
		logger.debug("조회 결과: {} 건", scheduleResponses.size());
		for (ScheduleFindResponse response : scheduleResponses) {
			logger.debug("{}: {} ~ {}", response.title(), response.startDateTime(), response.endDateTime());
		}
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

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			monthRepetitionSchedule, searchStartDate, searchEndDate);

		for (ScheduleFindResponse response : scheduleResponses) {
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

		for (ScheduleFindResponse response : scheduleResponses) {
			logger.debug("할일 : {}, {} ~ {}", response.title(), response.startDateTime(), response.endDateTime());
		}

		// then
		assertEquals(1, scheduleResponses.size());

	}
}
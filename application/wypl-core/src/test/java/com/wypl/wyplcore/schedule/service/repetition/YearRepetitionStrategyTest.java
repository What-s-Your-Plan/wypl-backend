package com.wypl.wyplcore.schedule.service.repetition;

import static com.wypl.wyplcore.ScheduleFixture.*;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.jpacalendardomain.schedule.domain.Schedule;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.strategy.YearRepetitionStrategy;

@SpringBootTest
class YearRepetitionStrategyTest {

	private static final Logger logger = LoggerFactory.getLogger(MonthRepetitionStrategyTest.class);

	private final YearRepetitionStrategy yearRepetitionStrategy = new YearRepetitionStrategy();
	private final Schedule yearRepetitionSchedule = YEARLY_SCHEDULE.toObject();

	@Test
	@DisplayName("DAY 조회 - 결과가 없는 경우")
	void getSchedulesResponsesForNoResult() {

		// given
		LocalDate startDate = YEARLY_SCHEDULE.getRepetitionStartDate()
			.with(TemporalAdjusters.firstDayOfYear());

		logger.info("searchStartDate : {} ~  searchEndDate : {}", startDate, startDate);

		// when
		List<ScheduleFindResponse> scheduleResponses = yearRepetitionStrategy.getScheduleResponses(
			yearRepetitionSchedule, startDate, startDate);

		// then
		for (ScheduleFindResponse response : scheduleResponses) {
			logger.info("할일 : {}, {} ~ {}", response.title(), response.startDateTime(), response.endDateTime());
		}
		assertEquals(0, scheduleResponses.size());

	}

	@Test
	@DisplayName("DAY 조회 - 결과가 1건인 경우")
	void getSchedulesResponsesForToday() {

		// given
		LocalDate startDate = YEARLY_SCHEDULE.getRepetitionStartDate().plusDays(1);

		// when
		List<ScheduleFindResponse> scheduleResponses = yearRepetitionStrategy.getScheduleResponses(
			yearRepetitionSchedule, startDate, startDate);

		// then
		assertEquals(1, scheduleResponses.size());

	}

	@Test
	@DisplayName("Year 검색 조건으로 반복 일정 조회")
	void getSchedulesResponsesForYear() {

		// given
		LocalDate startDate = YEARLY_SCHEDULE.getRepetitionStartDate().plusYears(1);
		LocalDate endDate = startDate.plusYears(2);
		logger.info("searchStartDate : {} ~  searchEndDate : {}", startDate, endDate);

		// when
		List<ScheduleFindResponse> scheduleResponses = yearRepetitionStrategy.getScheduleResponses(
			yearRepetitionSchedule, startDate, endDate);

		// then
		for (ScheduleFindResponse response : scheduleResponses) {
			logger.info("할일 : {}, {} ~ {}", response.title(), response.startDateTime(), response.endDateTime());
		}
		assertEquals(2, scheduleResponses.size());

	}

	@Test
	@DisplayName("특정 기간 조회 - 조회 시작 조건의 dayOfYear > 일정이 끝나는 날짜의 dayOfYear 인 경우")
	void getSchedulesResponsesForException() {

		// given
		LocalDate startDate = YEARLY_SCHEDULE.getRepetitionStartDate().plusDays(5);
		LocalDate endDate = startDate.plusYears(1);
		logger.info("조회 조건: {} ~ {}", startDate, endDate);

		// when
		List<ScheduleFindResponse> scheduleResponses = yearRepetitionStrategy.getScheduleResponses(
			yearRepetitionSchedule, startDate, endDate);

		// then
		logger.info("조회 결과: {} 건", scheduleResponses.size());
		for (ScheduleFindResponse response : scheduleResponses) {
			logger.info("{}: {} ~ {}", response.title(), response.startDateTime(), response.endDateTime());
		}
		assertEquals(1, scheduleResponses.size());

	}
}
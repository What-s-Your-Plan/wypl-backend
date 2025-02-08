package com.wypl.wyplcore.schedule.service.repetition;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.jpacalendardomain.schedule.domain.Schedule;
import com.wypl.wyplcore.ScheduleFixture;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

@SpringBootTest
class DayRepetitionStrategyTest {

	private Schedule dayRepetitionSchedule;

	@BeforeEach
	void setUpDayRepetitionSchedule() {
		dayRepetitionSchedule = mock(Schedule.class);
		when(dayRepetitionSchedule.getId()).thenReturn(1L);
		when(dayRepetitionSchedule.getTitle()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getTitle());
		when(dayRepetitionSchedule.getDescription()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getDescription());
		when(dayRepetitionSchedule.getStartDateTime()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getStartDateTime());
		when(dayRepetitionSchedule.getEndDateTime()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getEndDateTime());
		when(dayRepetitionSchedule.getRepetitionStartDate()).thenReturn(
			ScheduleFixture.DAILY_SCHEDULE.getRepetitionStartDate());
		when(dayRepetitionSchedule.getRepetitionEndDate()).thenReturn(
			ScheduleFixture.DAILY_SCHEDULE.getRepetitionEndDate());
		when(dayRepetitionSchedule.getRepetitionCycle()).thenReturn(
			ScheduleFixture.DAILY_SCHEDULE.getRepetitionCycle());
		when(dayRepetitionSchedule.getDayOfWeek()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getDayOfWeek());
		when(dayRepetitionSchedule.getWeekInterval()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getWeekInterval());
		when(dayRepetitionSchedule.isRepetition()).thenReturn(true);
	}

	@Test
	void getSchedulesResponsesForToday() {

		// given: dayRepetitionSchedule의 시작일로 오늘을 설정
		LocalDate startDate = dayRepetitionSchedule.getRepetitionStartDate();

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			dayRepetitionSchedule, startDate, startDate);

		// then
		assertEquals(1, scheduleResponses.size());
	}

	@Test
	void getSchedulesResponsesForWeek() {

		// given: dayRepetitionSchedule의 시작일 기준 다음 주 조회 (월요일 ~ 일요일)
		LocalDate startDate = dayRepetitionSchedule.getRepetitionStartDate().with(TemporalAdjusters.previousOrSame(
			DayOfWeek.MONDAY)).plusWeeks(1);
		LocalDate endDate = startDate.plusDays(6);

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			dayRepetitionSchedule, startDate, endDate);

		// then
		assertEquals(7, scheduleResponses.size());

	}

	@Test
	void getSchedulesResponsesForMonth() {

		// given: dayRepetitionSchedule의 시작일 기준 월 조회 (2024년 10월)
		LocalDate startDate = dayRepetitionSchedule.getRepetitionStartDate().with(TemporalAdjusters.firstDayOfMonth());
		LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

		// when
		List<ScheduleFindResponse> scheduleResponses = RepetitionService.getScheduleResponses(
			dayRepetitionSchedule, startDate, endDate);

		// then
		assertEquals(12, scheduleResponses.size());

	}
}
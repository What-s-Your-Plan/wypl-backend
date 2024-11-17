package com.wypl.wyplcore.schedule.service.repetition;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.ScheduleFixture;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.strategy.YearRepetitionStrategy;

@SpringBootTest
class YearRepetitionStrategyTest {

	private Schedule yearRepetitionSchedule;
	private final YearRepetitionStrategy yearRepetitionStrategy = new YearRepetitionStrategy();

	@BeforeEach
	void setUpYearRepetitionSchedule() {
		yearRepetitionSchedule = mock(Schedule.class);
		when(yearRepetitionSchedule.getId()).thenReturn(1L);
		when(yearRepetitionSchedule.getTitle()).thenReturn(ScheduleFixture.YEARLY_SCHEDULE.getTitle());
		when(yearRepetitionSchedule.getDescription()).thenReturn(ScheduleFixture.YEARLY_SCHEDULE.getDescription());
		when(yearRepetitionSchedule.getStartDateTime()).thenReturn(ScheduleFixture.YEARLY_SCHEDULE.getStartDateTime());
		when(yearRepetitionSchedule.getEndDateTime()).thenReturn(ScheduleFixture.YEARLY_SCHEDULE.getEndDateTime());
		when(yearRepetitionSchedule.getRepetitionStartDate()).thenReturn(ScheduleFixture.YEARLY_SCHEDULE.getRepetitionStartDate());
		when(yearRepetitionSchedule.getRepetitionEndDate()).thenReturn(ScheduleFixture.YEARLY_SCHEDULE.getRepetitionEndDate());
		when(yearRepetitionSchedule.getRepetitionCycle()).thenReturn(ScheduleFixture.YEARLY_SCHEDULE.getRepetitionCycle());
		when(yearRepetitionSchedule.getDayOfWeek()).thenReturn(ScheduleFixture.YEARLY_SCHEDULE.getDayOfWeek());
		when(yearRepetitionSchedule.getWeekInterval()).thenReturn(ScheduleFixture.YEARLY_SCHEDULE.getWeekInterval());
		when(yearRepetitionSchedule.isRepetition()).thenReturn(true);
	}

	@Test
	@DisplayName("오늘 검색 조건으로 반복일정 조회 - 결과가 없는 경우")
	void getSchedulesResponsesForNoResult(){

		// given
		LocalDate startDate = ScheduleFixture.YEARLY_SCHEDULE.getRepetitionStartDate().with(TemporalAdjusters.firstDayOfYear());

		// when
		List<ScheduleFindResponse> scheduleResponses = yearRepetitionStrategy.getScheduleResponses(
			yearRepetitionSchedule, startDate, startDate);

		// then
		assertEquals(0, scheduleResponses.size());

	}

	@Test
	@DisplayName("오늘 검색 조건으로 반복일정 조회 - 결과가 1건인 경우")
	void getSchedulesResponsesForToday(){

		// given
		LocalDate startDate = ScheduleFixture.YEARLY_SCHEDULE.getRepetitionStartDate().plusYears(1).plusDays(1);

		// when
		List<ScheduleFindResponse> scheduleResponses = yearRepetitionStrategy.getScheduleResponses(
			yearRepetitionSchedule, startDate, startDate);

		// then
		assertEquals(1, scheduleResponses.size());

	}

	@Test
	@DisplayName("Year 검색 조건으로 반복일정 조회")
	void getSchedulesResponsesForYear(){

		// given
		LocalDate startDate = ScheduleFixture.YEARLY_SCHEDULE.getRepetitionStartDate().plusDays(1);
		LocalDate endDate = startDate.plusYears(2);

		// when
		List<ScheduleFindResponse> scheduleResponses = yearRepetitionStrategy.getScheduleResponses(
			yearRepetitionSchedule, startDate, endDate);

		// then
		assertEquals(3, scheduleResponses.size());

	}
}
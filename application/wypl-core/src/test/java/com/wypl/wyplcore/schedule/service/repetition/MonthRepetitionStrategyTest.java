package com.wypl.wyplcore.schedule.service.repetition;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.ScheduleFixture;
import com.wypl.wyplcore.calendar.CalendarServiceUtil;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.strategy.MonthRepetitionStrategy;

@SpringBootTest
class MonthRepetitionStrategyTest {

	private Schedule monthRepetitionSchedule;
	private final MonthRepetitionStrategy monthRepetitionStrategy = new MonthRepetitionStrategy();

	@BeforeEach
	void setUpMonthRepetitionSchedule() {
		monthRepetitionSchedule = mock(Schedule.class);
		when(monthRepetitionSchedule.getId()).thenReturn(1L);
		when(monthRepetitionSchedule.getTitle()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getTitle());
		when(monthRepetitionSchedule.getDescription()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getDescription());
		when(monthRepetitionSchedule.getStartDateTime()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getStartDateTime());
		when(monthRepetitionSchedule.getEndDateTime()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getEndDateTime());
		when(monthRepetitionSchedule.getRepetitionStartDate()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionStartDate());
		when(monthRepetitionSchedule.getRepetitionEndDate()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionEndDate());
		when(monthRepetitionSchedule.getRepetitionCycle()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionCycle());
		when(monthRepetitionSchedule.getDayOfWeek()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getDayOfWeek());
		when(monthRepetitionSchedule.getWeekInterval()).thenReturn(ScheduleFixture.MONTHLY_SCHEDULE.getWeekInterval());
		when(monthRepetitionSchedule.isRepetition()).thenReturn(true);
	}

	@Test
	@DisplayName("오늘 검색 조건으로 반복일정 조회")
	void getSchedulesResponsesForToday(){

		// given: Set today as the start date of the monthRepetitionSchedule
		LocalDate startDate = ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionStartDate().plusMonths(1).plusDays(1);

		// when
		List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
			monthRepetitionSchedule, startDate, startDate);

		// then
		assertEquals(1, scheduleResponses.size());

	}

	@Test
	@DisplayName("Month 검색 조건으로 반복일정 조회")
	void getSchedulesResponsesForMonth(){

		// given: Set today as the start date of the monthRepetitionSchedule
		LocalDate startDate = ScheduleFixture.MONTHLY_SCHEDULE.getRepetitionStartDate().plusMonths(1).plusDays(1);
		LocalDate endDate = startDate.plusYears(1);

		System.out.println("startDate -> "+ startDate + ", endDate -> "+ endDate);

		// when
		List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
			monthRepetitionSchedule, startDate, endDate);

		// then
		assertEquals(3, scheduleResponses.size());

	}
}
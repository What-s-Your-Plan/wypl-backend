package com.wypl.wyplcore.calendar;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.wyplcore.ScheduleFixture;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

@SpringBootTest
class CalendarServiceUtilTest {

	private Schedule dayRepetitionSchedule;
	private Schedule weekRepetitionScheduleWithDayOfWeek;
	private Schedule weekRepetitionScheduleWithoutDayOfWeek;
	private Schedule monthRepetitionSchedule;
	private Schedule yearRepetitionSchedule;

	@BeforeEach
	void setUpDayRepetitionSchedule() {
		dayRepetitionSchedule = mock(Schedule.class);
		when(dayRepetitionSchedule.getId()).thenReturn(1L);
		when(dayRepetitionSchedule.getTitle()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getTitle());
		when(dayRepetitionSchedule.getDescription()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getDescription());
		when(dayRepetitionSchedule.getStartDateTime()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getStartDateTime());
		when(dayRepetitionSchedule.getEndDateTime()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getEndDateTime());
		when(dayRepetitionSchedule.getRepetitionStartDate()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getRepetitionStartDate());
		when(dayRepetitionSchedule.getRepetitionEndDate()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getRepetitionEndDate());
		when(dayRepetitionSchedule.getRepetitionCycle()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getRepetitionCycle());
		when(dayRepetitionSchedule.getDayOfWeek()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getDayOfWeek());
		when(dayRepetitionSchedule.getWeekInterval()).thenReturn(ScheduleFixture.DAILY_SCHEDULE.getWeekInterval());
		when(dayRepetitionSchedule.isRepetition()).thenReturn(true);
	}
	@BeforeEach
	void setUpWeekRepetitionSchedule() {
		weekRepetitionScheduleWithDayOfWeek = mock(Schedule.class);
		when(weekRepetitionScheduleWithDayOfWeek.getId()).thenReturn(1L);
		when(weekRepetitionScheduleWithDayOfWeek.getTitle()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE.getTitle());
		when(weekRepetitionScheduleWithDayOfWeek.getDescription()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE.getDescription());
		when(weekRepetitionScheduleWithDayOfWeek.getStartDateTime()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE.getStartDateTime());
		when(weekRepetitionScheduleWithDayOfWeek.getEndDateTime()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE.getEndDateTime());
		when(weekRepetitionScheduleWithDayOfWeek.getRepetitionStartDate()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionStartDate());
		when(weekRepetitionScheduleWithDayOfWeek.getRepetitionEndDate()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionEndDate());
		when(weekRepetitionScheduleWithDayOfWeek.getRepetitionCycle()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionCycle());
		when(weekRepetitionScheduleWithDayOfWeek.getDayOfWeek()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE.getDayOfWeek());
		when(weekRepetitionScheduleWithDayOfWeek.getWeekInterval()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE.getWeekInterval());
		when(weekRepetitionScheduleWithDayOfWeek.isRepetition()).thenReturn(true);
		when(weekRepetitionScheduleWithDayOfWeek.existsDayOfWeek()).thenReturn(true);
	}

	@BeforeEach
	void setUpWeekRepetitionScheduleWithoutDayOfWeek() {
		weekRepetitionScheduleWithoutDayOfWeek = mock(Schedule.class);
		when(weekRepetitionScheduleWithoutDayOfWeek.getId()).thenReturn(1L);
		when(weekRepetitionScheduleWithoutDayOfWeek.getTitle()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getTitle());
		when(weekRepetitionScheduleWithoutDayOfWeek.getDescription()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getDescription());
		when(weekRepetitionScheduleWithoutDayOfWeek.getStartDateTime()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getStartDateTime());
		when(weekRepetitionScheduleWithoutDayOfWeek.getEndDateTime()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getEndDateTime());
		when(weekRepetitionScheduleWithoutDayOfWeek.getRepetitionStartDate()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionStartDate());
		when(weekRepetitionScheduleWithoutDayOfWeek.getRepetitionEndDate()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionEndDate());
		when(weekRepetitionScheduleWithoutDayOfWeek.getRepetitionCycle()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionCycle());
		when(weekRepetitionScheduleWithoutDayOfWeek.getDayOfWeek()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getDayOfWeek());
		when(weekRepetitionScheduleWithoutDayOfWeek.getWeekInterval()).thenReturn(ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getWeekInterval());
		when(weekRepetitionScheduleWithoutDayOfWeek.isRepetition()).thenReturn(true);
		when(weekRepetitionScheduleWithoutDayOfWeek.existsDayOfWeek()).thenReturn(false);
	}

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


	@Nested
	class DayRepetitionTest {

		@Test
		void getSchedulesResponsesForToday() {

			// given: dayRepetitionSchedule의 시작일로 오늘을 설정
			LocalDate startDate = dayRepetitionSchedule.getRepetitionStartDate();

			// when
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
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
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
				dayRepetitionSchedule, startDate, endDate);

			// then
			assertEquals(7, scheduleResponses.size());

		}
		@Test
		void getSchedulesResponsesForMonth() {

			// given: dayRepetitionSchedule의 시작일 기준 월 조회 (2024년 10월)
			LocalDate startDate = dayRepetitionSchedule.getRepetitionStartDate().with(TemporalAdjusters.firstDayOfMonth());
			LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

			System.out.println("given -> "+ startDate +  " - "+ endDate);

			// when
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
				dayRepetitionSchedule, startDate, endDate);

			// then
			assertEquals(12, scheduleResponses.size());

		}

	}


	@Nested
	class WeekRepetitionTest{

		@Test
		void getSchedulesResponsesForTodayWithDayOfWeek(){

			// given: weekRepetitionSchedule의 시작일로 오늘을 설정
			LocalDate startDate = ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionStartDate().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));

			// when
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
				weekRepetitionScheduleWithDayOfWeek, startDate, startDate);

			// then
			assertEquals(1, scheduleResponses.size());
		}

		@Test
		void getSchedulesResponsesForWeekWithDayOfWeek(){

			// given: weekRepetitionSchedule의 시작일 기준 다음 주 조회 (월요일 ~ 일요일)
			LocalDate startDate = ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionStartDate().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
			LocalDate endDate = startDate.plusDays(6);

			// when
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
				weekRepetitionScheduleWithDayOfWeek, startDate, endDate);

			// then
			assertEquals(3, scheduleResponses.size());
		}

		@Test
		void getSchedulesResponsesForMonthWithDayOfWeek(){

			// given: weekRepetitionSchedule의 시작일 기준 월 조회 (2024년 10월)
			LocalDate startDate = ScheduleFixture.WEEKLY_SCHEDULE.getRepetitionStartDate().with(TemporalAdjusters.firstDayOfMonth());
			LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());

			// when
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
				weekRepetitionScheduleWithDayOfWeek, startDate, endDate);

			// then
			for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
				System.out.println(scheduleResponse.title() + " : "+ scheduleResponse.startDateTime() + " - " + scheduleResponse.endDateTime());
			}
			assertEquals(5, scheduleResponses.size());

		}

		@Test
		@DisplayName("요일이 설정되지 않은 일정의 DAY 검색 조건으로 반복일정 조회")
		void getSchedulesResponsesForTodayWithoutDayOfWeek(){

			// given: Set today after 1 week, and the range is 'RepetitionStartDate 요일 < today < RepetitionEndDate 요일'
			LocalDate today = ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionStartDate().plusDays(1).plusWeeks(1);
			System.out.println("today -> "+ today);

			// when
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
				weekRepetitionScheduleWithoutDayOfWeek, today, today);

			// then
			for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
				System.out.println(scheduleResponse.title() + " : "+ scheduleResponse.startDateTime() + " - " + scheduleResponse.endDateTime());
			}
			assertEquals(1, scheduleResponses.size());
		}

		@Test
		@DisplayName("요일이 설정되지 않은 일정의 WEEK 검색 조건으로 반복일정 조회")
		void getSchedulesResponsesForWeekWithoutDayOfWeek(){

			// given: Set today after 1 week, and the range is 'RepetitionStartDate 요일 < today < RepetitionEndDate 요일'
			LocalDate startDate = ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionStartDate().with(TemporalAdjusters.nextOrSame(DayOfWeek.MONDAY));
			LocalDate endDate = startDate.plusDays(6);
			System.out.println("startDate -> "+ startDate + ", endDate -> "+ endDate);

			// when
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
				weekRepetitionScheduleWithoutDayOfWeek, startDate, endDate);

			// then
			for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
				System.out.println(scheduleResponse.title() + " : "+ scheduleResponse.startDateTime() + " - " + scheduleResponse.endDateTime());
			}
			assertEquals(1, scheduleResponses.size());
		}

		@Test
		@DisplayName("요일이 설정되지 않은 일정의 WEEK 검색 조건으로 반복일정 조회 - 반복 주에 포함되지 않는 조회 조건")
		void getSchedulesResponsesForWeekWithoutDayOfWeek2(){

			// given: Set today after 1 week, and the range is 'RepetitionStartDate 요일 < today < RepetitionEndDate 요일'
			LocalDate startDate = ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionStartDate().plusWeeks(1);
			LocalDate endDate = startDate.plusDays(6);

			// when
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
				weekRepetitionScheduleWithoutDayOfWeek, startDate, endDate);

			// then
			for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
				System.out.println(scheduleResponse.title() + " : "+ scheduleResponse.startDateTime() + " - " + scheduleResponse.endDateTime());
			}
			assertEquals(0, scheduleResponses.size());
		}

		@Test
		@DisplayName("요일이 설정되지 않은 일정의 MONTH 검색 조건으로 반복일정 조회")
		void getSchedulesResponsesForMonthWithoutDayOfWeek(){

			// given: Set today after 1 week, and the range is 'RepetitionStartDate 요일 < today < RepetitionEndDate 요일'
			LocalDate startDate = ScheduleFixture.WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK.getRepetitionStartDate().plusMonths(1).with(TemporalAdjusters.firstDayOfMonth());
			LocalDate endDate = startDate.with(TemporalAdjusters.lastDayOfMonth());
			System.out.println("startDate -> "+ startDate + ", endDate -> "+ endDate);

			// when
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
				weekRepetitionScheduleWithoutDayOfWeek, startDate, endDate);

			// then
			for (ScheduleFindResponse scheduleResponse : scheduleResponses) {
				System.out.println(scheduleResponse.title() + " : "+ scheduleResponse.startDateTime() + " - " + scheduleResponse.endDateTime());
			}
			assertEquals(2, scheduleResponses.size());
		}

	}

	@Nested
	class MonthRepetitionTest{

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

	@Nested
	class YearRepetitionTest{

		@Test
		@DisplayName("오늘 검색 조건으로 반복일정 조회 - 결과가 없는 경우")
		void getSchedulesResponsesForNoResult(){

			// given
			LocalDate startDate = ScheduleFixture.YEARLY_SCHEDULE.getRepetitionStartDate().with(TemporalAdjusters.firstDayOfYear());

			// when
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
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
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
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
			List<ScheduleFindResponse> scheduleResponses = CalendarServiceUtil.getScheduleResponses(
				yearRepetitionSchedule, startDate, endDate);

			// then
			assertEquals(3, scheduleResponses.size());

		}
	}

}
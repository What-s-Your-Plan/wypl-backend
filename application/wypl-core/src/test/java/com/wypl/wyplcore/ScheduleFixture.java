package com.wypl.wyplcore;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.wypl.jpacalendardomain.calendar.data.RepetitionCycle;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpacalendardomain.calendar.domain.ScheduleInfo;

import lombok.Getter;

@Getter
public enum ScheduleFixture {

	DAILY_SCHEDULE(
		"일일 일정 1",
		"매일 오전 9시에서 오전 10시까지 반복되는 일정입니다.",
		LocalDateTime.of(2024, 10, 20, 9, 0),
		LocalDateTime.of(2024, 10, 20, 10, 0),
		LocalDate.of(2024, 10, 20),
		LocalDate.of(2024, 12, 31),
		RepetitionCycle.DAY,
		null,
		null
	),
	DAILY_SCHEDULE_2(
		"일일 일정 2",
		"매일 오후 한시에서 오후 2시 반까지 반복되는 일정입니다.",
		LocalDateTime.of(2024, 10, 20, 13, 0),
		LocalDateTime.of(2024, 10, 20, 14, 30),
		LocalDate.of(2024, 10, 20),
		LocalDate.of(2025, 12, 31),
		RepetitionCycle.DAY,
		null,
		null
	),
	WEEKLY_SCHEDULE(
		"주간 일정",
		"매주 월,수,금에 반복되는 일정입니다.",
		LocalDateTime.of(2024, 10, 20, 14, 0),
		LocalDateTime.of(2024, 10, 20, 15, 0),
		LocalDate.of(2024, 10, 20),
		LocalDate.of(2025, 1, 1),
		RepetitionCycle.WEEK,
		((1 << 1) | (1 << 3) | (1 << 5)), // 월, 수, 금 반복
		1 // 매주 반복
	),
	WEEKLY_SCHEDULE_WITHOUT_DAY_OF_WEEK(
		"2주 마다 반복되는 일정",
		"2주 마다 반복되는 일정입니다.",
		LocalDateTime.of(2024, 10, 20, 14, 0),
		LocalDateTime.of(2024, 10, 21, 14, 0),
		LocalDate.of(2024, 10, 20),
		LocalDate.of(2025, 1, 1),
		RepetitionCycle.WEEK,
		null, // 월, 수, 금 반복
		2 // 매주 반복
	),
	MONTHLY_SCHEDULE(
		"월간 일정",
		"매달 첫째 주 화요일에 반복되는 일정입니다.",
		LocalDateTime.of(2024, 10, 20, 10, 0),
		LocalDateTime.of(2024, 10, 21, 11, 0),
		LocalDate.of(2024, 10, 20),
		LocalDate.of(2025, 2, 1),
		RepetitionCycle.MONTH,
		2, // 화요일
		1 // 매월 반복
	),
	YEARLY_SCHEDULE(
		"연간 일정",
		"매년 10월 20일에 반복되는 일정입니다.",
		LocalDateTime.of(2024, 10, 20, 10, 0),
		LocalDateTime.of(2024, 10, 21, 11, 0),
		LocalDate.of(2024, 10, 20),
		LocalDate.of(2026, 10, 20),
		RepetitionCycle.YEAR,
		null,
		null
	);

	private final String title;
	private final String description;
	private final LocalDateTime startDateTime;
	private final LocalDateTime endDateTime;
	private final LocalDate repetitionStartDate;
	private final LocalDate repetitionEndDate;
	private final RepetitionCycle repetitionCycle;
	private final Integer dayOfWeek;
	private final Integer weekInterval;

	ScheduleFixture(String title, String description, LocalDateTime startDateTime, LocalDateTime endDateTime,
		LocalDate repetitionStartDate, LocalDate repetitionEndDate, RepetitionCycle repetitionCycle,
		Integer dayOfWeek, Integer weekInterval) {
		this.title = title;
		this.description = description;
		this.startDateTime = startDateTime;
		this.endDateTime = endDateTime;
		this.repetitionStartDate = repetitionStartDate;
		this.repetitionEndDate = repetitionEndDate;
		this.repetitionCycle = repetitionCycle;
		this.dayOfWeek = dayOfWeek;
		this.weekInterval = weekInterval;
	}

	public Schedule toEntity(ScheduleInfo scheduleInfo) {
		return Schedule.builder()
			.scheduleInfo(scheduleInfo)
			.title(this.title)
			.description(this.description)
			.startDateTime(this.startDateTime)
			.endDateTime(this.endDateTime)
			.repetitionStartDate(this.repetitionStartDate)
			.repetitionEndDate(this.repetitionEndDate)
			.repetitionCycle(this.repetitionCycle)
			.dayOfWeek(this.dayOfWeek)
			.weekInterval(this.weekInterval)
			.build();
	}

	public Schedule toObject() {
		return Schedule.builder()
			.title(this.title)
			.description(this.description)
			.startDateTime(this.startDateTime)
			.endDateTime(this.endDateTime)
			.repetitionStartDate(this.repetitionStartDate)
			.repetitionEndDate(this.repetitionEndDate)
			.repetitionCycle(this.repetitionCycle)
			.dayOfWeek(this.dayOfWeek)
			.weekInterval(this.weekInterval)
			.build();
	}

}
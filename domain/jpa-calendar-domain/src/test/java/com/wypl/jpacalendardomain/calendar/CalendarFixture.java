package com.wypl.jpacalendardomain.calendar;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;

public enum CalendarFixture {
	wyplProject("와플 개발 일정", "와플 스터디 개발 일정 달력입니다.", 1L, true);

	private final String name;
	private final String description;
	private final long ownerId;
	private final boolean isShared;

	CalendarFixture(String name, String description, long ownerId, boolean isShared) {
		this.name = name;
		this.description = description;
		this.ownerId = ownerId;
		this.isShared = isShared;
	}

	public Calendar toCalendar() {
		return Calendar.builder()
			.name(name)
			.description(description)
			.ownerId(ownerId)
			.isShared(isShared)
			.build();
	}
}
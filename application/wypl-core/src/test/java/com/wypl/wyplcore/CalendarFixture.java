package com.wypl.wyplcore;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;

import lombok.Getter;

@Getter
public enum CalendarFixture {

	PRIVATE_CALENDAR("개인 달력", "개인 달력입니다.", 1L, false),
	GROUP_CALENDAR1("와플 그룹 달력", "와플 화이팅", 2L, true),
	GROUP_CALENDAR2("CS 스터디 그룹 달력", "CS 재밋다!", 3L, true);

	private final String name;
	private final String description;
	private final Long ownerId;
	private final Boolean isShared;

	CalendarFixture(String name, String description, Long ownerId, Boolean isShared) {
		this.name = name;
		this.description = description;
		this.ownerId = ownerId;
		this.isShared = isShared;
	}

	public Calendar toEntity() {
		return Calendar.builder()
			.name(this.name)
			.description(this.description)
			.ownerId(this.ownerId)
			.isShared(this.isShared)
			.build();
	}

}
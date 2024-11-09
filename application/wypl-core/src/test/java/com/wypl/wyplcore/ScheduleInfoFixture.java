package com.wypl.wyplcore;

import java.util.Collections;

import com.wypl.jpacalendardomain.calendar.domain.Calendar;
import com.wypl.jpacalendardomain.calendar.domain.ScheduleInfo;

import lombok.Getter;

@Getter
public enum ScheduleInfoFixture {

	PRIVATE_SCHEDULE_INFO(1L),
	GROUP_SCHEDULE_INFO_1(2L),
	GROUP_SCHEDULE_INFO_2( 3L);

	private final Long creatorId;

	ScheduleInfoFixture(Long creatorId) {
		this.creatorId = creatorId;
	}

	public ScheduleInfo toEntity(Calendar calendar) {
		return ScheduleInfo.builder()
			.calendar(calendar)
			.creatorId(this.creatorId)
			.build();
	}

}
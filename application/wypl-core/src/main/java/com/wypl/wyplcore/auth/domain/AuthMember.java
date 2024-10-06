package com.wypl.wyplcore.auth.domain;

import com.wypl.jpacalendardomain.calendar.data.ConvertibleScheduleInfo;

public record AuthMember(
	long id
) implements ConvertibleScheduleInfo {

	@Override
	public Long getCreatorId() {
		return id();
	}

}

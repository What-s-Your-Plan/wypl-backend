package com.wypl.wyplcore.schedule.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpacalendardomain.calendar.domain.ScheduleInfo;

public record ScheduleInfoCreateResponse(

	@JsonProperty("calendar_id")
	Long calendarId,

	@JsonProperty("schedule_info_id")
	Long scheduleInfoId

) {

	public static ScheduleInfoCreateResponse from(Schedule schedule) {
		ScheduleInfo scheduleInfo = schedule.getScheduleInfo();
		return new ScheduleInfoCreateResponse(
			scheduleInfo.getCalendar().getId(),
			scheduleInfo.getId()
		);
	}
}

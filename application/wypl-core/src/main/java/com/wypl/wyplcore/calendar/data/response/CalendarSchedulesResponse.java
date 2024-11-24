package com.wypl.wyplcore.calendar.data.response;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

public record CalendarSchedulesResponse(

	@JsonProperty("schedule_count")
	int scheduleCount,

	@JsonProperty("schedules")
	List<ScheduleFindResponse> schedules
){
}

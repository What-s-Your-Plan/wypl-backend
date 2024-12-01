package com.wypl.wyplcore.calendar.data.request;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.wyplcore.schedule.data.CalendarType;

public record CalendarFindRequest(

	@JsonProperty("calendar_type")
	CalendarType calendarType,

	@JsonProperty("start_date")
	LocalDate today
) {
}

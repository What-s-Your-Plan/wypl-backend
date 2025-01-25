package com.wypl.wyplcore.schedule.data.response;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;

public record ScheduleFindResponse(

	@JsonProperty("schedule_id")
	Long scheduleId,

	@JsonProperty("title")
	String title,

	@JsonProperty("description")
	String description,

	@JsonProperty("start_date_time")
	LocalDateTime startDateTime,

	@JsonProperty("end_date_time")
	LocalDateTime endDateTime

) {

	public static ScheduleFindResponse of(Schedule schedule, LocalDateTime startDateTime, LocalDateTime endDateTime) {
		return new ScheduleFindResponse(
			schedule.getId(),
			schedule.getTitle(),
			schedule.getDescription(),
			startDateTime,
			endDateTime
		);
	}

	public static ScheduleFindResponse of(Schedule schedule, LocalDate startDate, LocalDate endDate) {
		LocalDateTime startDateTime = LocalDateTime.of(startDate, schedule.getStartDateTime().toLocalTime());
		LocalDateTime endDateTime = LocalDateTime.of(endDate, schedule.getEndDateTime().toLocalTime());
		return of(schedule, startDateTime, endDateTime);
	}
}

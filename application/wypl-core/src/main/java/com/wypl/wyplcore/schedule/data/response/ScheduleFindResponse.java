package com.wypl.wyplcore.schedule.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.jpacalendardomain.calendar.domain.Schedule;

import java.time.LocalDateTime;

public record ScheduleFindResponse(

        @JsonProperty("schedule_id")
        long scheduleId,

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
}

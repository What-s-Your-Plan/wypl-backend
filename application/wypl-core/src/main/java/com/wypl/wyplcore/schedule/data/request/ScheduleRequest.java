package com.wypl.wyplcore.schedule.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.jpascheduledomain.schedule.data.ConvertibleSchedule;

import java.time.LocalDateTime;

public record ScheduleRequest (

        String title,

        String description,

        @JsonProperty("start_date")
        LocalDateTime startDateTime,

        @JsonProperty("end_date")
        LocalDateTime endDateTime
) implements ConvertibleSchedule {
        @Override
        public String getTitle() {
                return title;
        }

        @Override
        public String getDescription() {
                return description;
        }

        @Override
        public LocalDateTime getStartDateTime() {
                return startDateTime;
        }

        @Override
        public LocalDateTime getEndDateTime() {
                return endDateTime;
        }
}

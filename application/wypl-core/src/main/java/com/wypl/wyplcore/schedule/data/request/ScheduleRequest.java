package com.wypl.wyplcore.schedule.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;

public record ScheduleRequest (

        String title,

        String description,

        @JsonProperty("start_date")
        LocalDateTime startDate,

        @JsonProperty("end_date")
        LocalDateTime endDate
){
}

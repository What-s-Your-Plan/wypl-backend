package com.wypl.wyplcore.schedule.data.response;

import com.fasterxml.jackson.annotation.JsonProperty;

public record ScheduleInfoCreateResponse (

        @JsonProperty("calendar_id")
        Long calendarId,

        @JsonProperty("schedule_info_id")
        Long scheduleInfoId

){
}

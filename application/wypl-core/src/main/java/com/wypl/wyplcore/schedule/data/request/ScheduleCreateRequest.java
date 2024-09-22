package com.wypl.wyplcore.schedule.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

@Builder
public record ScheduleCreateRequest(

        @JsonProperty("calendar_id")
        long calenderId,

        @JsonProperty("schedule_request")
        ScheduleRequest scheduleRequest

) {

}
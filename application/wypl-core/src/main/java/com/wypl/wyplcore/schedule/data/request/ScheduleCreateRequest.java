package com.wypl.wyplcore.schedule.data.request;

import lombok.Builder;

@Builder
public record ScheduleCreateRequest(

        ScheduleInfoRequest scheduleInfoRequest,

        ScheduleRequest scheduleRequest,

        RepetitionRequest repetitionRequest

) {
}
package com.wypl.wyplcore.schedule.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.jpascheduledomain.schedule.data.ConvertibleScheduleInfo;

import java.time.LocalDateTime;

public record ScheduleInfoRequest (

        @JsonProperty("calendar_id")
        int calenderId,

        @JsonProperty("creator_id")
        long creatorId,

        @JsonProperty("start_date")
        LocalDateTime startDate,

        @JsonProperty("end_date")
        LocalDateTime endDate
) implements ConvertibleScheduleInfo {


        @Override
        public Long getCreatorId() {
                return creatorId;
        }

        @Override
        public LocalDateTime getStartDateTime() {
                return startDate;
        }

        @Override
        public LocalDateTime getEndDateTime() {
                return endDate;
        }
}

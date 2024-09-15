package com.wypl.wyplcore.schedule.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.jpascheduledomain.schedule.data.ConvertibleRepetition;
import com.wypl.jpascheduledomain.schedule.data.RepetitionCycle;

public record RepetitionRequest(

        @JsonProperty("repetition_cycle")
        RepetitionCycle repetitionCycle,

        @JsonProperty("day_of_week")
        int dayOfWeek,

        @JsonProperty("week_interval")
        Integer weekInterval

) implements ConvertibleRepetition {
        @Override
        public RepetitionCycle getRepetitionCycle() {
                return repetitionCycle;
        }

        @Override
        public Integer getDayOfWeek() {
                return dayOfWeek;
        }

        @Override
        public Integer getWeekInterval() {
                return weekInterval;
        }
}
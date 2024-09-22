package com.wypl.wyplcore.schedule.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.jpacalendardomain.calendar.data.ConvertibleSchedule;
import com.wypl.jpacalendardomain.calendar.data.RepetitionCycle;

import java.time.LocalDate;
import java.time.LocalDateTime;

public record ScheduleRequest (

        String title,

        String description,

        @JsonProperty("start_datetime")
        LocalDateTime startDateTime,

        @JsonProperty("end_datetime")
        LocalDateTime endDateTime,

        @JsonProperty("repetition_cycle")
        RepetitionCycle repetitionCycle,

        @JsonProperty("day_of_week")
        int dayOfWeek,

        @JsonProperty("week_interval")
        Integer weekInterval,

        @JsonProperty("repetition_start_date")
        LocalDate repetitionStartDate,

        @JsonProperty("repetition_end_date")
        LocalDate repetitionEndDate

) implements ConvertibleSchedule {
        @Override
        public String getTitle() {
                return this.title;
        }

        @Override
        public String getDescription() {
                return this.description;
        }

        @Override
        public LocalDateTime getStartDateTime() {
                return this.startDateTime;
        }

        @Override
        public LocalDateTime getEndDateTime() {
                return this.endDateTime;
        }

        @Override
        public LocalDate getRepetitionStartDate() {
                return this.repetitionStartDate;
        }

        @Override
        public LocalDate getRepetitionEndDate() {
                return this.repetitionEndDate;
        }

        @Override
        public RepetitionCycle getRepetitionCycle() {
                return this.repetitionCycle;
        }

        @Override
        public Integer getDayOfWeek() {
                return this.dayOfWeek;
        }

        @Override
        public Integer getWeekInterval() {
                return this.weekInterval;
        }
}

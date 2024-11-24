package com.wypl.wyplcore.calendar.data.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.wypl.wyplcore.schedule.data.CalendarType;

import java.time.LocalDate;

public record CalendarFindRequest (

        @JsonProperty("calendar_type")
        CalendarType calendarType,

        @JsonProperty("start_date")
        LocalDate today
){
}

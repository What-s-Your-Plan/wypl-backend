package com.wypl.wyplcore.calendar.service.strategy;


import com.wypl.wyplcore.schedule.data.CalendarType;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

import java.time.LocalDate;
import java.util.List;

public interface CalendarStrategy {

    CalendarType getCalendarType();

    List<ScheduleFindResponse> getAllSchedule(long calendarId, LocalDate startDate);
}

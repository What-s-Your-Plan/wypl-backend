package com.wypl.wyplcore.calendar.data.response;

import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;

import java.util.List;

public record FindCalendarResponse(

        CalendarFindResponse calender,

        List<ScheduleFindResponse> schedules
) {
}

package com.wypl.wyplcore.calendar.service.strategy;

import com.wypl.wyplcore.schedule.data.CalendarType;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class WeekCalendarStrategy implements CalendarStrategy {

    @Override
    public CalendarType getCalendarType() {
        return CalendarType.WEEK;
    }

    @Override
    public List<ScheduleFindResponse> getAllSchedule(long calendarId, LocalDate startDate) {
        return List.of();
    }
}

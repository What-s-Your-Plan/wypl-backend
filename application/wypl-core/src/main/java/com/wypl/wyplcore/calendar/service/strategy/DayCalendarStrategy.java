package com.wypl.wyplcore.calendar.service.strategy;


import com.wypl.jpacalendardomain.calendar.repository.CalendarRepository;
import com.wypl.wyplcore.schedule.data.CalendarType;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@RequiredArgsConstructor
public class DayCalendarStrategy implements CalendarStrategy {

    private final CalendarRepository calendarRepository;

    @Override
    public CalendarType getCalendarType() {
        return CalendarType.DAY;
    }

    @Override
    public List<ScheduleFindResponse> getAllSchedule(long calendarId, LocalDate startDate) {
        return List.of();
    }
}

package com.wypl.wyplcore.calendar.service.strategy;

import com.wypl.jpacalendardomain.calendar.domain.Schedule;
import com.wypl.jpacalendardomain.calendar.repository.ScheduleRepository;
import com.wypl.wyplcore.calendar.data.DateSearchCondition;
import com.wypl.wyplcore.schedule.data.CalendarType;
import com.wypl.wyplcore.schedule.data.response.ScheduleFindResponse;
import com.wypl.wyplcore.schedule.service.repetition.RepetitionService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.List;

@Component
@RequiredArgsConstructor
public class WeekCalendarStrategy implements CalendarStrategy {

    @Override
    public CalendarType getCalendarType() {
        return CalendarType.WEEK;
    }

    @Override
    public DateSearchCondition getDateSearchCondition(LocalDate today) {
        LocalDate searchStartDate = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
        LocalDate searchEndDate = today.plusDays(6);
        return new DateSearchCondition(searchStartDate, searchEndDate);
    }
}

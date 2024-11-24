package com.wypl.wyplcore.calendar.service.strategy;

import com.wypl.wyplcore.calendar.data.DateSearchCondition;
import com.wypl.wyplcore.schedule.data.CalendarType;

import java.time.LocalDate;

public interface CalendarStrategy {

    CalendarType getCalendarType();

    DateSearchCondition getDateSearchCondition(LocalDate today);
}

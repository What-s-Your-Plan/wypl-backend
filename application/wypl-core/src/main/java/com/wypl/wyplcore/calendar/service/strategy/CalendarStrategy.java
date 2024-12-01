package com.wypl.wyplcore.calendar.service.strategy;

import java.time.LocalDate;

import com.wypl.wyplcore.calendar.data.DateSearchCondition;
import com.wypl.wyplcore.schedule.data.CalendarType;

public interface CalendarStrategy {

	CalendarType getCalendarType();

	DateSearchCondition getDateSearchCondition(LocalDate today);
}
